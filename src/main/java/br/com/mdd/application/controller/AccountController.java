package br.com.mdd.application.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mdd.application.repository.AccountRepository;
import br.com.mdd.application.service.UserService;
import br.com.mdd.domain.model.Account;
import br.com.mdd.domain.model.User;
import br.com.mdd.presentation.view.model.AccountViewModel;

@Controller
public class AccountController {
	
	@Autowired
	@Qualifier("accountDAO")
	private AccountRepository accountRepository;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/accounts")
	public String accountsHome(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		List<AccountViewModel> accounts = getAccounts(user).stream()
				.map(a -> AccountViewModel.fromAccount(a))
				.collect(Collectors.toList());
		
		BigDecimal totalAccounts = BigDecimal.ZERO;
		for (AccountViewModel a : accounts) {
			totalAccounts = totalAccounts.add(a.getBalance());
		}
		
		model.addAttribute("accounts", accounts);
		model.addAttribute("totalAccounts", totalAccounts);
		
		return "/accounts/accountsHome";
	}
	
	@RequestMapping(value = "/newAccount", method = RequestMethod.GET)
	public String newAccount(Model model){
		AccountViewModel account = new AccountViewModel();
		model.addAttribute("account", account);
		
		return "/accounts/account";
	}

	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public String saveAccount(@ModelAttribute(value = "account") AccountViewModel account, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		
		BigDecimal balance = null;
		if (account.getId() != null) {
			balance = account.getBalance();
		} else {
			balance = account.getStartBalance();
		}
		
		Account a = new Account(account.getInstitutionName(), balance, account.getType()); 
		a.setId(account.getId());
		a.setUser(user);
		
		accountRepository.save(a);
		
		return newAccount(model);
	}
	
	@RequestMapping("/manageAccount/{id}")
	public String updateInvestment(Model model, @PathVariable Integer id){
		
		Account a = accountRepository.find(id, Account.class);
		
		AccountViewModel account = AccountViewModel.fromAccount(a);
		
		model.addAttribute("account", account);
		
		return "/accounts/account";
	}
	
	@RequestMapping(value="/deleteAccount/{id}")
	public String deleteInvestment(Model model, @PathVariable Integer id, @RequestParam(required=false) Integer mes) {
		Account account = accountRepository.find(id, Account.class);
		accountRepository.remove(account);
		
		return "redirect:/accounts";
	}
	
	private List<Account> getAccounts(User user){
		List<Account> accounts = accountRepository.findByUser(user);
		
		return accounts;
	}
}
