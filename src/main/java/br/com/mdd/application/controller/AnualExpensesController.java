package br.com.mdd.application.controller;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.persistence.dao.ExpenseDAO;
import br.com.mdd.persistence.dao.GenericDAO;
import br.com.mdd.presentation.view.model.AnualFixedExpensesBudgetViewModel;
import br.com.mdd.presentation.view.model.ExpenseViewModel;

@Controller
public class AnualExpensesController {
	
	private Set<Expense> despesasFixas;
	
	private Set<Expense> despesasVariaveis;
	
	private Set<Category> categorias;
	
	private ExpenseViewModel despesa;
	
	@Autowired
	private ExpenseDAO expensesDAO;
	
	@Autowired
	@Qualifier("genericDAO")
	private GenericDAO<Category> categoriesDAO;
	
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
		Budget orcamentoDespesasFixas = new Budget(despesasFixas).anual().withPrediction().generate();
		Budget orcamentoDespesasVariaveis = new Budget(despesasVariaveis).anual().generate();
		AnualFixedExpensesBudgetViewModel orcamentoViewModelDespesasFixasViewModel = new AnualFixedExpensesBudgetViewModel(orcamentoDespesasFixas);
		AnualFixedExpensesBudgetViewModel orcamentoViewModelDespesasVariaveisViewModel = new AnualFixedExpensesBudgetViewModel(orcamentoDespesasVariaveis);
		model.addAttribute("orcamentoDespesasFixas", orcamentoViewModelDespesasFixasViewModel);
		model.addAttribute("orcamentoDespesasVariaveis", orcamentoViewModelDespesasVariaveisViewModel);
	}
	
	@RequestMapping(value = "/despesa", method = RequestMethod.POST)
	public String salvar(@ModelAttribute(value = "despesa") ExpenseViewModel despesa, Model model) {
		Expense d = null;
		if(despesa.getDespesaFixa()) {
			d = new FixedExpense(despesa.getDescricao(), despesa.getValor(), despesa.getDataLancamento());
		} else {
			d = new Expense(despesa.getDescricao(), despesa.getValor(), despesa.getDataLancamento());
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
	
	@Transactional(readOnly=true)
	@RequestMapping("/excluirDespesa/{id}")
	public String excluirDespesa(Model model, @PathVariable Integer id){
		
		Expense expense = expensesDAO.find(id, Expense.class);
		expensesDAO.remove(expense);
		
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
	
	private Set<Expense> getDespesasFixas(){
		Set<Expense> despesas = new TreeSet<Expense>(expensesDAO.findAllFixedExpenses());
		
		return despesas;	
	}
	
	private Set<Expense> getDespesasVariaveis(){
		Set<Expense> despesas = new TreeSet<Expense>(expensesDAO.findAllVariableExpenses());
		
		return despesas;	
	}
	
	private Set<Category> getCategorias() {
		Set<Category> categorias = new TreeSet<>(categoriesDAO.findAll(Category.class));
		
		return categorias;
	}

}
