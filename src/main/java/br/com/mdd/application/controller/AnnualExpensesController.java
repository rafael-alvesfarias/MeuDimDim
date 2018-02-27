package br.com.mdd.application.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

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
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.persistence.dao.CategoryDAO;
import br.com.mdd.persistence.dao.ExpenseDAO;
import br.com.mdd.presentation.view.model.expense.AnualExpensesBudgetViewModel;
import br.com.mdd.presentation.view.model.expense.ExpenseViewModel;

@Controller
public class AnnualExpensesController {
	
	private Set<FixedExpense> despesasFixas;
	
	private Set<Expense> despesasVariaveis;
	
	private Set<Category> categorias;
	
	@Autowired
	private ExpenseDAO expensesDAO;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@RequestMapping("/expenses/annual")
	public String despesasAnuais(Model model){
		despesasFixas = getFixedExpenses();
		despesasVariaveis = getVariableExpenses();
		categorias = getCategories();
		ExpenseViewModel expenseForm = new ExpenseViewModel();
		expenseForm.setCategories(categorias);
		model.addAttribute("expense", expenseForm);
		
		gerarOrcamentoAnual(model);
		
		return "/budgets/annualExpenses";
	}

	private void gerarOrcamentoAnual(Model model) {
		Budget<FixedExpense> orcamentoDespesasFixas = new BudgetBuilder<FixedExpense>(despesasFixas)
				.annual()
				.withPrediction()
				.build()
				.generate();
		Budget<Expense> orcamentoDespesasVariaveis = new BudgetBuilder<Expense>(despesasVariaveis)
				.annual()
				.build()
				.generate();
		AnualExpensesBudgetViewModel<FixedExpense> orcamentoDespesasFixasViewModel;
		AnualExpensesBudgetViewModel<Expense> orcamentoDespesasVariaveisViewModel = new AnualExpensesBudgetViewModel<Expense>(orcamentoDespesasVariaveis);
		@SuppressWarnings("unchecked")
		Map<Integer, Integer> exclusionsMap = (Map<Integer, Integer>) session.getAttribute("exclusionsMap");
		if (exclusionsMap != null) {
			orcamentoDespesasFixasViewModel = new AnualExpensesBudgetViewModel<FixedExpense>(orcamentoDespesasFixas, exclusionsMap);
		} else {
			orcamentoDespesasFixasViewModel = new AnualExpensesBudgetViewModel<FixedExpense>(orcamentoDespesasFixas);
		}
		model.addAttribute("orcamentoDespesasFixas", orcamentoDespesasFixasViewModel);
		model.addAttribute("orcamentoDespesasVariaveis", orcamentoDespesasVariaveisViewModel);
	}
	
	@Transactional(readOnly=true)
	@RequestMapping("/editarDespesa/{id}")
	public String editarDespesa(Model model, @PathVariable Integer id){
		
		Expense expense = expensesDAO.find(id, Expense.class);
		
		gerarOrcamentoAnual(model);
		
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
		
		return despesasAnuais(model);
	}
	
	private Set<FixedExpense> getFixedExpenses(){
		Set<FixedExpense> despesas = new TreeSet<FixedExpense>(expensesDAO.findAllFixedExpenses());
		
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
