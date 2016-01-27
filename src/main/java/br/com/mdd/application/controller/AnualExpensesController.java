package br.com.mdd.application.controller;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.domain.model.Budget;
import br.com.mdd.presentation.model.ExpenseViewModel;
import br.com.mdd.presentation.model.AnualFixedExpensesBudgetViewModel;

@Controller
public class AnualExpensesController {
	
	private Set<Expense> despesasFixas = getDespesasFixas();
	
	private Set<Expense> despesasVariaveis = getDespesasVariaveis();
	
	private Set<Category> categorias = getCategorias();
	
	@RequestMapping("/despesasAnuais")
	public String despesasAnuais(Model model){
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
			despesasFixas.add(d);
		} else {
			d = new Expense(despesa.getDescricao(), despesa.getValor(), despesa.getDataLancamento());
			despesasVariaveis.add(d);
		}
		d.setCategory(getCategoria(despesa.getCategoria()));
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
	
	private static final Set<Expense> getDespesasFixas(){
		Set<Expense> despesas = new TreeSet<Expense>();
		
		FixedExpense agua = new FixedExpense("Água", new BigDecimal("45.98"), new LocalDate(2016, 1, 10));
		FixedExpense luz = new FixedExpense("Luz", new BigDecimal("98.73"), new LocalDate(2016, 1, 12));
		FixedExpense telefone = new FixedExpense("Telefone", new BigDecimal("55.3"), new LocalDate(2016, 1, 20));
		
		despesas.add(agua);
		despesas.add(luz);
		despesas.add(telefone);
		
		return despesas;	
	}
	
	private static final Set<Expense> getDespesasVariaveis(){
		Set<Expense> despesas = new TreeSet<Expense>();
		
		Expense combustivel = new Expense("Combustível", new BigDecimal("150"), new LocalDate(2016, 1, 5));
		Expense lazer = new Expense("Lazer", new BigDecimal("250"), new LocalDate(2016, 1, 5));
		Expense alimentacao = new Expense("Alimentação", new BigDecimal("400"), new LocalDate(2016, 1, 5));
		
		despesas.add(combustivel);
		despesas.add(lazer);
		despesas.add(alimentacao);
		
		return despesas;	
	}
	
	private static final Set<Category> getCategorias() {
		Set<Category> categorias = new TreeSet<>();
		categorias.add(new Category("alimentacao", "Alimentação"));
		categorias.add(new Category("transporte", "Transporte"));
		categorias.add(new Category("saude", "Saúde"));
		categorias.add(new Category("lazer", "Lazer"));
		categorias.add(new Category("moradia", "Moradia"));
		
		return categorias;
	}

}
