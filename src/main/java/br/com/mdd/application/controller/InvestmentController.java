package br.com.mdd.application.controller;

import java.security.Principal;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
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
import br.com.mdd.application.repository.EntryRepository;
import br.com.mdd.application.service.EntryService;
import br.com.mdd.application.service.UserService;
import br.com.mdd.domain.model.Account;
import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.BudgetBuilder;
import br.com.mdd.domain.model.Entry;
import br.com.mdd.domain.model.Investment;
import br.com.mdd.domain.model.User;
import br.com.mdd.presentation.view.model.AccountViewModel;
import br.com.mdd.presentation.view.model.BudgetViewModel;
import br.com.mdd.presentation.view.model.investment.InvestmentViewModel;

@Controller
public class InvestmentController {
	
	private static final String NUMBER_MONTHS_DEFAULT = "3";
	
	@Autowired
	@Qualifier("entryDAO")
	private EntryRepository<Investment> investmentDAO;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EntryService entryService;
	
	@Autowired
	@Qualifier("accountDAO")
	private AccountRepository accountRepository;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/investments")
	public String investmentHome(Model model, @RequestParam(required = false, defaultValue = NUMBER_MONTHS_DEFAULT) int numberMonths,
			Principal principal) {
		User user = userService.findByUsername(principal.getName());
		Set<Entry> investments = getInvestments(user);
		model.addAttribute("investmentBudgets", this.generateBudgets(Integer.valueOf(numberMonths), investments));
		
		return "/investments/investmentHome";
	}
	
	@RequestMapping(value = "/newInvestment", method = RequestMethod.GET)
	public String newInvestment(Model model, Principal principal){
		User user = userService.findByUsername(principal.getName());
		InvestmentViewModel investment = new InvestmentViewModel();
		Collection<AccountViewModel> accounts = getAccounts(user).stream()
				.map(a -> AccountViewModel.fromAccount(a))
				.collect(Collectors.toList());
		investment.setAccounts(accounts);
		model.addAttribute("investment", investment);
		
		return "/investments/investment";
	}

	@RequestMapping(value = "/investment", method = RequestMethod.POST)
	public String saveInvestment(@ModelAttribute(value = "investment") InvestmentViewModel investment, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		Investment i = new Investment(investment.getName(), investment.getValue(), new LocalDate(investment.getDueDate()), investment.getReturnRate());
		i.setId(investment.getId());
		if (investment.getEntryDate() != null) {
			i.setEntryDate(new LocalDate(investment.getEntryDate()));
		}
		if (investment.getWithdrawlDate() != null) {
			i.setWithdrawlDate(new LocalDate(investment.getWithdrawlDate()));
		}
		if (investment.getTaxRate() != null) {
			i.setTaxRate(investment.getTaxRate());
		}
		i.setAccount(getAccount(investment.getAccount()));
		i.setUser(user);
		
		if (investment.isPosted() && investment.getEntryDate() == null) {
			entryService.postEntry(i, i.getAccount());
		} else {
			investmentDAO.save(i);
		}
		
		return "redirect:/newInvestment";
	}
	
	@RequestMapping("/editInvestment/{id}")
	public String updateInvestment(Model model, Principal principal, @PathVariable Integer id) {
		User user = userService.findByUsername(principal.getName());
		Investment i = investmentDAO.find(id, Investment.class);
		InvestmentViewModel investment = InvestmentViewModel.fromInvestment(i);
		Collection<AccountViewModel> accounts = getAccounts(user).stream()
				.map(a -> AccountViewModel.fromAccount(a))
				.collect(Collectors.toList());
		investment.setAccounts(accounts);
		model.addAttribute("investment", investment);
		
		return "/investments/investment";
	}
	
	@RequestMapping(value="/deleteInvestment/{id}")
	public String deleteInvestment(Model model, @PathVariable Integer id, @RequestParam(required=false) Integer mes) {
		Investment investment = investmentDAO.find(id, Investment.class);
		//Verifica se investimento não gerada automaticamente
		if (mes != null && mes > investment.getDueDate().getMonthOfYear()) {
			@SuppressWarnings("unchecked")
			Map<Integer, Integer> exclusionsMap = (Map<Integer, Integer>) session.getAttribute("exclusionsMap");
			if (exclusionsMap == null) {
				exclusionsMap = new HashMap<Integer, Integer>();
				session.setAttribute("exclusionsMap", exclusionsMap);
			}
			exclusionsMap.put(id, mes);
		} else {
			investmentDAO.remove(investment);
		}
		
		return "/investments/investmentHome";
	}
	
	private List<BudgetViewModel> generateBudgets(int numberMonths, Set<Entry> entries) {
		List<BudgetViewModel> budgets = new ArrayList<>();
		LocalDate dateFrom = LocalDate.now().withDayOfMonth(1);
		LocalDate dateTo = dateFrom.plusMonths(numberMonths);
		Budget<Entry> budget = new BudgetBuilder<Entry>(entries)
				.withPeriod(dateFrom, dateTo)
				.build();
		for (int i = 0; i < numberMonths; i++) {
			budgets.add(this.generateBudget(dateFrom.plusMonths(i), budget));
		}
		return budgets;
	}
	
	private BudgetViewModel generateBudget(LocalDate dateIt, Budget<Entry> budget) {
		Month month = Month.of(dateIt.getMonthOfYear());
		String monthStr = dateIt.toString("MMMM/yyyy");
		Budget<Entry> b = budget.subBudget(monthStr, month);
		return BudgetViewModel.fromBudget(b);
	}
	
	private Set<Entry> getInvestments(User user){
		Set<Entry> investments = new TreeSet<Entry>(investmentDAO.findByUser(user, Investment.class));
		
		return investments;
	}
	
	private Collection<Account> getAccounts(User user){
		Collection<Account> investments = new ArrayList<Account>(accountRepository.findByUser(user));
		
		return investments;
	}
	
	private Account getAccount(Integer id) {
		return accountRepository.find(id, Account.class);
	}
}
