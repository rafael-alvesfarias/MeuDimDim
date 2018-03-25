package br.com.mdd.application.controller;

import java.security.Principal;
import java.time.Month;
import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mdd.application.repository.AccountRepository;
import br.com.mdd.application.repository.CategoryRepository;
import br.com.mdd.application.repository.EntryRepository;
import br.com.mdd.application.service.EntryService;
import br.com.mdd.application.service.UserService;
import br.com.mdd.domain.model.Account;
import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.BudgetBuilder;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Entry;
import br.com.mdd.domain.model.Category.CategoryType;
import br.com.mdd.domain.model.Income;
import br.com.mdd.domain.model.User;
import br.com.mdd.presentation.view.model.AccountViewModel;
import br.com.mdd.presentation.view.model.BudgetViewModel;
import br.com.mdd.presentation.view.model.income.IncomeViewModel;

@Controller
public class IncomesController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EntryService entryService;
	
	@Autowired
	@Qualifier("entryDAO")
	private EntryRepository<Income> incomeRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private HttpSession session;
	
	@Transactional(readOnly=true)
	@RequestMapping("/incomes")
	public String incomesHome(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		
		Set<Entry> incomes = getIncomes(user);
		
		model.addAttribute("incomeBudgets", this.generateBudgets(3, incomes));
		
		return "/incomes/incomesHome";
	}
	
	@RequestMapping(value = "/newIncome", method = RequestMethod.GET)
	public String newIncome(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		
		IncomeViewModel income = new IncomeViewModel();
		income.setCategories(getCategories());
		List<AccountViewModel> accounts = getAccounts(user).stream().map(a -> AccountViewModel.fromAccount(a)).collect(Collectors.toList());
		income.setAccounts(accounts);
		model.addAttribute("income", income);
		
		return "/incomes/income";
	}

	@Transactional
	@RequestMapping(value = "/income", method = RequestMethod.POST)
	public String saveIncome(@ModelAttribute(value = "income") IncomeViewModel income, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		
		Income i = new Income(income.getName(), income.getValue(), new LocalDate(income.getDueDate()));
		i.setId(income.getId());
		if (income.getEntryDate() != null) {
			i.setEntryDate(new LocalDate(income.getEntryDate()));
		}
		if (income.getCategory() != null) {
			i.setCategory(getCategory(income.getCategory()));
		}
		i.setAccount(getAccount(income.getAccount()));
		i.setUser(user);
		if (income.isReceived() && income.getEntryDate() == null) {
			entryService.postEntry(i, i.getAccount());
		} else {
			incomeRepository.save(i);
		}
		
		return "redirect:/newIncome";
	}
	
	@Transactional(readOnly=true)
	@RequestMapping("/editIncome/{id}")
	public String updateIncome(Model model, @PathVariable Integer id){
		Income i = incomeRepository.find(id, Income.class);
		
		IncomeViewModel income = IncomeViewModel.fromIncome(i);
		income.setCategories(getCategories());
		List<AccountViewModel> accounts = getAccounts(i.getUser()).stream().map(a -> AccountViewModel.fromAccount(a)).collect(Collectors.toList());
		income.setAccounts(accounts);
		
		model.addAttribute("income", income);
		
		return "/incomes/income";
	}
	
	@Transactional
	@RequestMapping(value="/deleteIncome/{id}")
	public String deleteIncome(Model model, @PathVariable Integer id, @RequestParam(required=false) Integer mes) {
		Income income = incomeRepository.find(id, Income.class);
		//Verifica se receita não gerada automaticamente
		if (mes != null && mes > income.getDueDate().getMonthOfYear()) {
			@SuppressWarnings("unchecked")
			Map<Integer, Integer> exclusionsMap = (Map<Integer, Integer>) session.getAttribute("exclusionsMap");
			if (exclusionsMap == null) {
				exclusionsMap = new HashMap<Integer, Integer>();
				session.setAttribute("exclusionsMap", exclusionsMap);
			}
			exclusionsMap.put(id, mes);
		} else {
			incomeRepository.remove(income);
		}
		
		return "/home";
	}
	
	private Category getCategory(Integer id) {
		return categoryRepository.find(id, Category.class);
	}
	
	private Set<Category> getCategories() {
		Set<Category> categorias = new TreeSet<>(categoryRepository.findAllCategoriesByType(CategoryType.INCOME));
		
		return categorias;
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
	
	private Account getAccount(Integer id) {
		return accountRepository.find(id, Account.class);
	}
	
	private Set<Entry> getIncomes(User user){
		Set<Entry> despesas = new TreeSet<Entry>(incomeRepository.findByUser(user, Income.class));
		
		return despesas;
	}
	
	private List<Account> getAccounts(User user) {
		List<Account> categorias = new ArrayList<>(accountRepository.findByUser(user));
		
		return categorias;
	}

}
