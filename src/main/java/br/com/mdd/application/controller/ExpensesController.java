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
import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mdd.application.repository.AccountRepository;
import br.com.mdd.application.repository.CategoryRepository;
import br.com.mdd.application.repository.ExpenseRepository;
import br.com.mdd.application.service.EntryService;
import br.com.mdd.application.service.UserService;
import br.com.mdd.domain.model.Account;
import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.BudgetBuilder;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Category.CategoryType;
import br.com.mdd.domain.model.Entry;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.domain.model.User;
import br.com.mdd.domain.model.VariableExpense;
import br.com.mdd.presentation.view.model.AccountViewModel;
import br.com.mdd.presentation.view.model.BudgetViewModel;
import br.com.mdd.presentation.view.model.expense.ExpenseViewModel;

@Controller
public class ExpensesController {
	
	private static final String NUMBER_MONTHS_DEFAULT = "3";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EntryService entryService;
	
	@Autowired
	private ExpenseRepository expensesDAO;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly=true)
	@RequestMapping("/expenses")
	public String expensesHome(Model model, Principal principal ,
			@RequestParam(required = false, defaultValue = NUMBER_MONTHS_DEFAULT) int numberMonths) {
		User user = userService.findByUsername(principal.getName());
		
		Set<Entry> fixedExpenses = getFixedExpenses(user);
		Set<Entry> variableExpenses = getVariableExpenses(user);
		Set<Category> categories = getCategories();
		
		ExpenseViewModel expenseForm = new ExpenseViewModel();
		expenseForm.setCategories(categories);
		
		model.addAttribute("expense", expenseForm);
		model.addAttribute("fixedExpensesBudgets", this.generateBudgets(Integer.valueOf(numberMonths), fixedExpenses, true));
		model.addAttribute("variableExpensesBudgets", this.generateBudgets(Integer.valueOf(numberMonths), variableExpenses, false));
		
		return "/expenses/expensesHome";
	}
	
	@RequestMapping("/newExpense")
	public String expense(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		
		Set<Category> categories = getCategories();
		ExpenseViewModel expense = new ExpenseViewModel();
		expense.setCategories(categories);
		List<AccountViewModel> accounts = getAccounts(user).stream().map(a -> AccountViewModel.fromAccount(a)).collect(Collectors.toList());
		expense.setAccounts(accounts);
		model.addAttribute("expense", expense);
		
		return "/expenses/expense";
	}
	
	@Transactional(readOnly=true)
	@RequestMapping("/editExpense/{id}")
	public String editarDespesa(Model model, Principal principal, @PathVariable Integer id) {
		User user = userService.findByUsername(principal.getName());
		Expense expense = expensesDAO.find(id, Expense.class);
		
		ExpenseViewModel expenseForm = ExpenseViewModel.fromExpense(expense);
		expenseForm.setCategories(getCategories());
		List<AccountViewModel> accounts = getAccounts(user).stream().map(a -> AccountViewModel.fromAccount(a)).collect(Collectors.toList());
		expenseForm.setAccounts(accounts);
		
		model.addAttribute("expense", expenseForm);
		
		return "/expenses/expense";
	}
	
	@Transactional
	@RequestMapping("/deleteExpense/{id}")
	public String excluirDespesa(Model model, @PathVariable Integer id, @RequestParam(required=false) Integer mes) {
		Expense expense = expensesDAO.find(id, Expense.class);
		//Verifica se despesa não gerada automáticamente
		if (mes != null && mes > expense.getDueDate().getMonthOfYear()) {
			@SuppressWarnings("unchecked")
			Map<Integer, Integer> exclusionsMap = (Map<Integer, Integer>) session.getAttribute("exclusionsMap");
			if (exclusionsMap == null) {
				exclusionsMap = new HashMap<Integer, Integer>();
				session.setAttribute("exclusionsMap", exclusionsMap);
			}
			exclusionsMap.put(id, mes);
		} else {
			expensesDAO.remove(expense);
		}
		
		return "redirect:/expenses";
	}

	@Transactional
	@RequestMapping(value = "/expense", method = RequestMethod.POST)
	public String salvar(@ModelAttribute(value = "expense") @Valid ExpenseViewModel expense,
			Principal principal, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "/expenses/expense";
		}
		
		User user = userService.findByUsername(principal.getName());
		
		Expense d = null;
		if (expense.isFixedExpense()) {
			d = new FixedExpense(expense.getName(), expense.getValue(), new LocalDate(expense.getDueDate()));
		} else {
			d = new VariableExpense(expense.getName(), expense.getValue(), new LocalDate(expense.getDueDate()));
		}
		
		d.setId(expense.getId());
		if (expense.getEntryDate() != null) {
			d.setEntryDate(new LocalDate(expense.getEntryDate()));
		}
		if (expense.getCategory() != null) {
			d.setCategory(getCategory(expense.getCategory()));
		}
		d.setAccount(getAccount(expense.getAccount()));
		d.setUser(user);
		if (expense.isPaid() && expense.getEntryDate() == null) {
			entryService.postEntry(d, d.getAccount());
		} else {
			expensesDAO.save(d);
		}
		
		return "redirect:/newExpense";
	}
	
	private List<BudgetViewModel> generateBudgets(int numberMonths, Set<Entry> entries, boolean predict) {
		List<BudgetViewModel> budgets = new ArrayList<>();
		LocalDate dateFrom = LocalDate.now().withDayOfMonth(1);
		LocalDate dateTo = dateFrom.plusMonths(numberMonths);
		BudgetBuilder<Entry> builder = new BudgetBuilder<Entry>(entries)
				.withPeriod(dateFrom, dateTo);
		if (predict) {
			builder.withPrediction();
		}
		Budget<Entry> budget = builder.build();
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
	
	private Category getCategory(Integer id) {
		return categoryRepository.find(id, Category.class);
	}
	
	private Account getAccount(Integer id) {
		return accountRepository.find(id, Account.class);
	}
	
	private Set<Entry> getFixedExpenses(User user){
		Set<Entry> despesas = new TreeSet<Entry>(expensesDAO.findFixedExpensesByUser(user));
		
		return despesas;
	}
	
	private Set<Entry> getVariableExpenses(User user){
		Set<Entry> despesas = new TreeSet<Entry>(expensesDAO.findVariableExpensesByUser(user));
		
		return despesas;	
	}
	
	private Set<Category> getCategories() {
		Set<Category> categorias = new TreeSet<>(categoryRepository.findAllCategoriesByType(CategoryType.EXPENSE));
		
		return categorias;
	}
	
	private List<Account> getAccounts(User user) {
		List<Account> categorias = new ArrayList<>(accountRepository.findByUser(user));
		
		return categorias;
	}

}
