package br.com.mdd.application.controller;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.persistence.dao.GenericDAO;
import br.com.mdd.presentation.view.model.AnualFixedExpensesBudgetViewModel;
import br.com.mdd.presentation.view.model.ExpenseViewModel;

@Controller
public class AnualExpensesController {
	
	private Set<Expense> despesasFixas;
	
	private Set<Expense> despesasVariaveis;
	
	private Set<Category> categorias;
	
	@Autowired
	private GenericDAO<Expense> expensesDAO;
	
	@Autowired
	private GenericDAO<FixedExpense> fixedExpensesDAO;
	
	@Autowired
	private GenericDAO<Category> categoriesDAO;
	
	@RequestMapping("/despesasAnuais")
	public String despesasAnuais(Model model){
		despesasFixas = getDespesasFixas();
		despesasVariaveis = getDespesasVariaveis();
		categorias = getCategorias();
		
		Budget orcamentoDespesasFixas = new Budget(despesasFixas).anual().withPrediction().generate();
		Budget orcamentoDespesasVariaveis = new Budget(despesasVariaveis).anual().generate();
		AnualFixedExpensesBudgetViewModel orcamentoViewModelDespesasFixasViewModel = new AnualFixedExpensesBudgetViewModel(orcamentoDespesasFixas);
		AnualFixedExpensesBudgetViewModel orcamentoViewModelDespesasVariaveisViewModel = new AnualFixedExpensesBudgetViewModel(orcamentoDespesasVariaveis);
		model.addAttribute("orcamentoDespesasFixas", orcamentoViewModelDespesasFixasViewModel);
		model.addAttribute("orcamentoDespesasVariaveis", orcamentoViewModelDespesasVariaveisViewModel);
		ExpenseViewModel despesa = new ExpenseViewModel();
		despesa.setCategorias(categorias);
		model.addAttribute("despesa", despesa);
		
		return "/despesas/despesasAnuais";
	}
	
	@RequestMapping(value = "/despesa", method = RequestMethod.POST)
	public String salvar(@ModelAttribute(value = "despesa") ExpenseViewModel despesa, Model model) {
		Expense d = null;
		if(despesa.getDespesaFixa()) {
			d = new FixedExpense(despesa.getDescricao(), despesa.getValor(), despesa.getDataLancamento());
		} else {
			d = new Expense(despesa.getDescricao(), despesa.getValor(), despesa.getDataLancamento());
		}
		
		d.setPaid(despesa.getPago());
		d.setCategory(getCategoria(despesa.getCategoria()));
		expensesDAO.save(d);
		
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
		Set<Expense> despesas = new TreeSet<Expense>(fixedExpensesDAO.findAll(FixedExpense.class));
		
		return despesas;	
	}
	
	private Set<Expense> getDespesasVariaveis(){
		Set<Expense> despesas = new TreeSet<Expense>(expensesDAO.findAll(Expense.class));
		
		return despesas;	
	}
	
	private Set<Category> getCategorias() {
		Set<Category> categorias = new TreeSet<>(categoriesDAO.findAll(Category.class));
		
		return categorias;
	}

}
