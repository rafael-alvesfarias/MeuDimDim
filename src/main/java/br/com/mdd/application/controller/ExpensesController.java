package br.com.mdd.application.controller;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

import br.com.mdd.application.repository.CategoryRepository;
import br.com.mdd.application.repository.ExpenseRepository;
import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.BudgetBuilder;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Entry;
import br.com.mdd.domain.model.Category.CategoryType;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.domain.model.VariableExpense;
import br.com.mdd.presentation.view.model.BudgetViewModel;
import br.com.mdd.presentation.view.model.expense.ExpenseViewModel;

@Controller
public class ExpensesController {
	
	private static final String NUMBER_MONTHS_DEFAULT = "3";
	
	private Set<Entry> fixedExpenses;
	
	private Set<Entry> variableExpenses;
	
	private Set<Category> categories;
	
	private ExpenseViewModel expense;
	
	@Autowired
	private ExpenseRepository expensesDAO;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CategoryRepository categoryDAO;
	
	@RequestMapping("/expenses")
	public String expensesHome(Model model, 
			@RequestParam(required = false, defaultValue = NUMBER_MONTHS_DEFAULT) int numberMonths){
		fixedExpenses = getFixedExpenses();
		variableExpenses = getVariableExpenses();
		categories = getCategories();
		ExpenseViewModel expenseForm = new ExpenseViewModel();
		expenseForm.setCategories(categories);
		
		model.addAttribute("expense", expenseForm);
		model.addAttribute("fixedExpensesBudgets", this.generateBudgets(Integer.valueOf(numberMonths), fixedExpenses, true));
		model.addAttribute("variableExpensesBudgets", this.generateBudgets(Integer.valueOf(numberMonths), variableExpenses, false));
		
		return "/expenses/expensesHome";
	}
	
	@RequestMapping("/newExpense")
	public String expense(Model model){
		categories = getCategories();
		expense = new ExpenseViewModel();
		expense.setCategories(categories);
		model.addAttribute("expense", expense);
		
		return "/expenses/expense";
	}
	
	@Transactional(readOnly=true)
	@RequestMapping("/editExpense/{id}")
	public String editarDespesa(Model model, @PathVariable Integer id){
		
		Expense expense = expensesDAO.find(id, Expense.class);
		
		ExpenseViewModel expenseForm = ExpenseViewModel.fromExpense(expense);
		expenseForm.setCategories(categories);
		
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
		
		return expensesHome(model, 3);
	}

	@RequestMapping(value = "/expense", method = RequestMethod.POST)
	public String salvar(@ModelAttribute(value = "expense") @Valid ExpenseViewModel expense
			,BindingResult bindingResult , Model model) {
		if (bindingResult.hasErrors()) {
			return "/expenses/expense";
		}
		Expense d = null;
		if(expense.isFixedExpense()) {
			d = new FixedExpense(expense.getName(), expense.getValue(), new LocalDate(expense.getDueDate()));
		} else {
			d = new VariableExpense(expense.getName(), expense.getValue(), new LocalDate(expense.getDueDate()));
		}
		d.setId(expense.getId());
		
		d.setPaid(expense.isPaid());
		d.setCategory(getCategory(expense.getCategory()));
		expensesDAO.save(d);
		
		return expense(model);
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
	
	private Category getCategory(String nome) {
		for (Category categoria : categories) {
			if (categoria.getName().equals(nome)) {
				return categoria;
			}
		}
		return null;
	}
	
	private Set<Entry> getFixedExpenses(){
		Set<Entry> despesas = new TreeSet<Entry>(expensesDAO.findAllFixedExpenses());
		
		return despesas;
	}
	
	private Set<Entry> getVariableExpenses(){
		Set<Entry> despesas = new TreeSet<Entry>(expensesDAO.findAllVariableExpenses());
		
		return despesas;	
	}
	
	private Set<Category> getCategories() {
		Set<Category> categorias = new TreeSet<>(categoryDAO.findAllCategoriesByType(CategoryType.EXPENSE));
		
		return categorias;
	}

}
