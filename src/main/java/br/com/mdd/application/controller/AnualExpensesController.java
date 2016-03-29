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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Category.CategoryType;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.domain.model.VariableExpense;
import br.com.mdd.persistence.dao.CategoryDAO;
import br.com.mdd.persistence.dao.ExpenseDAO;
import br.com.mdd.presentation.view.model.AnualExpensesBudgetViewModel;
import br.com.mdd.presentation.view.model.ExpenseViewModel;

@Controller
public class AnualExpensesController {
	
	private Set<FixedExpense> despesasFixas;
	
	private Set<Expense> despesasVariaveis;
	
	private Set<Category> categorias;
	
	private ExpenseViewModel despesa;
	
	@Autowired
	private ExpenseDAO expensesDAO;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@RequestMapping("/despesasAnuais")
	public String despesasAnuais(Model model){
		despesasFixas = getDespesasFixas();
		despesasVariaveis = getDespesasVariaveis();
		categorias = getCategorias();
		despesa = new ExpenseViewModel();
		despesa.setCategorias(categorias);
		model.addAttribute("despesa", despesa);
		
		gerarOrcamentoAnual(model);
		
		return "/despesas/despesasAnuais";
	}

	private void gerarOrcamentoAnual(Model model) {
		Budget<FixedExpense> orcamentoDespesasFixas = new Budget<FixedExpense>(despesasFixas).annual().withPrediction().generate();
		Budget<Expense> orcamentoDespesasVariaveis = new Budget<Expense>(despesasVariaveis).annual().generate();
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
	
	@RequestMapping(value = "/despesa", method = RequestMethod.POST)
	public String salvar(@ModelAttribute(value = "despesa") ExpenseViewModel despesa, Model model) {
		Expense d = null;
		if(despesa.getDespesaFixa()) {
			d = new FixedExpense(despesa.getDescricao(), despesa.getValor(), despesa.getDataLancamento());
		} else {
			d = new VariableExpense(despesa.getDescricao(), despesa.getValor(), despesa.getDataLancamento());
		}
		d.setId(despesa.getId());
		
		d.setPaid(despesa.getPago());
		d.setCategory(getCategoria(despesa.getCategoria()));
		expensesDAO.save(d);
		
		return despesasAnuais(model);
	}
	
	@Transactional(readOnly=true)
	@RequestMapping("/editarDespesa/{id}")
	public String editarDespesa(Model model, @PathVariable Integer id){
		
		Expense expense = expensesDAO.find(id, Expense.class);
		
		gerarOrcamentoAnual(model);
		
		despesa = ExpenseViewModel.fromExpense(expense);
		despesa.setCategorias(categorias);
		
		model.addAttribute("despesa", despesa);
		
		return "/despesas/despesasAnuais";
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
	
	private Category getCategoria(String nome) {
		for (Category categoria : categorias) {
			if (categoria.getName().equals(nome)) {
				return categoria;
			}
		}
		return null;
	}
	
	private Set<FixedExpense> getDespesasFixas(){
		Set<FixedExpense> despesas = new TreeSet<FixedExpense>(expensesDAO.findAllFixedExpenses());
		
		return despesas;	
	}
	
	private Set<Expense> getDespesasVariaveis(){
		Set<Expense> despesas = new TreeSet<Expense>(expensesDAO.findAllVariableExpenses());
		
		return despesas;	
	}
	
	private Set<Category> getCategorias() {
		Set<Category> categorias = new TreeSet<>(categoryDAO.findAllCategoriesByType(CategoryType.EXPENSE));
		
		return categorias;
	}

}
