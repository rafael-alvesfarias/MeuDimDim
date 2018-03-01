package br.com.mdd.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.BudgetBuilder;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Category.CategoryType;
import br.com.mdd.domain.model.Entry;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.persistence.dao.CategoryDAO;
import br.com.mdd.persistence.dao.ExpenseDAO;
import br.com.mdd.presentation.view.model.BudgetViewModel;
import br.com.mdd.presentation.view.model.expense.AnualExpensesBudgetViewModel;
import br.com.mdd.presentation.view.model.expense.ExpenseViewModel;

@Controller
public class AnnualExpensesController {
	
	private static final String NUMBER_MONTHS_DEFAULT = "3";
	
	private Set<Entry> despesasFixas;
	
	private Set<Expense> despesasVariaveis;
	
	private Set<Category> categorias;
	
	@Autowired
	private ExpenseDAO expensesDAO;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@RequestMapping("/expenses/annual")
	public String despesasAnuais(Model model, 
			@RequestParam(required = false, defaultValue = NUMBER_MONTHS_DEFAULT) int numberMonths){
		despesasFixas = getFixedExpenses();
		despesasVariaveis = getVariableExpenses();
		categorias = getCategories();
		ExpenseViewModel expenseForm = new ExpenseViewModel();
		expenseForm.setCategories(categorias);
		
		model.addAttribute("expense", expenseForm);
		model.addAttribute("fixedExpensesBudgets", this.generateBudget(Integer.valueOf(numberMonths), despesasFixas));
		
		return "/budgets/annualExpenses";
	}
	
	private List<BudgetViewModel> generateBudget(int numberMonths, Set<Entry> entries) {
		List<BudgetViewModel> budgets = new ArrayList<>();
		LocalDate dateFrom = LocalDate.now().withDayOfMonth(1);
		LocalDate dateTo = dateFrom.plusMonths(numberMonths);
		Budget<Entry> budget = new BudgetBuilder<Entry>(entries)
				.withPeriod(dateFrom, dateTo)
				.withPrediction()
				.build()
				.generate();
		LocalDate dateIt = dateFrom;
		for (int i = 0; i < numberMonths; i++) {
			dateIt = dateIt.plusMonths(1);
			int month = dateIt.getMonthOfYear();
			String monthStr = dateIt.toString("MMMM");
			Budget<Entry> b = budget.subBudget(monthStr, month).generate();
			budgets.add(BudgetViewModel.fromBudget(b));
		}
		return budgets;
	}
	
	@Transactional(readOnly=true)
	@RequestMapping("/editarDespesa/{id}")
	public String editarDespesa(Model model, @PathVariable Integer id){
		
		Expense expense = expensesDAO.find(id, Expense.class);
		
		ExpenseViewModel expenseForm = ExpenseViewModel.fromExpense(expense);
		expenseForm.setCategories(categorias);
		
		model.addAttribute("expense", expenseForm);
		
		return "/expenses/expense";
	}
	
	@Transactional
	@RequestMapping("/excluirDespesa/{id}")
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
		
		return despesasAnuais(model, 3);
	}
	
	private Set<Entry> getFixedExpenses(){
		Set<Entry> despesas = new TreeSet<Entry>(expensesDAO.findAllFixedExpenses());
		
		return despesas;
	}
	
	private Set<Expense> getVariableExpenses(){
		Set<Expense> despesas = new TreeSet<Expense>(expensesDAO.findAllVariableExpenses());
		
		return despesas;	
	}
	
	private Set<Category> getCategories() {
		Set<Category> categorias = new TreeSet<>(categoryDAO.findAllCategoriesByType(CategoryType.EXPENSE));
		
		return categorias;
	}

}
