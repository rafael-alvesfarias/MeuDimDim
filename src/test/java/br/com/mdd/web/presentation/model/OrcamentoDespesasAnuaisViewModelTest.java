package br.com.mdd.web.presentation.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.presentation.view.model.expense.AnualExpensesBudgetViewModel;
import br.com.mdd.presentation.view.model.expense.AnualExpensesBudgetViewModel.ConjuntoDespesas;
import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.BudgetBuilder;

public class OrcamentoDespesasAnuaisViewModelTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGeraTabelaOrcamentoAnualComDespesasFixasDeJaneiro() {
		int year = LocalDate.now().getYear();
		Set<FixedExpense> despesas = new HashSet<FixedExpense>();
		FixedExpense agua = new FixedExpense("Água", new BigDecimal("45.98"), new LocalDate(year, 1, 10));
		FixedExpense luz = new FixedExpense("Luz", new BigDecimal("98.73"), new LocalDate(year, 1, 12));
		FixedExpense telefone = new FixedExpense("Telefone", new BigDecimal("55.3"), new LocalDate(year, 1, 20));
		despesas.add(agua);
		despesas.add(luz);
		despesas.add(telefone);
		Budget<FixedExpense> orcamento = new BudgetBuilder<FixedExpense>(despesas).annual().withPrediction().build();
		
		AnualExpensesBudgetViewModel<FixedExpense> tabela = new AnualExpensesBudgetViewModel<FixedExpense>(orcamento);
		
		//Conferindo as despesasFixas planejadas
		for (ConjuntoDespesas conj : tabela.getDespesasAnuais()) {
			assertEquals(12, conj.getDespesas().size());
		}
		
		//Conferindo os totais dos conjuntos
		for (ConjuntoDespesas conj : tabela.getDespesasAnuais()) {
			if(conj.getNomeDespesa().equalsIgnoreCase("Água")){
				assertEquals(new BigDecimal("551.76"), conj.getTotal());
			}else if(conj.getNomeDespesa().equalsIgnoreCase("Luz")){
				assertEquals(new BigDecimal("1184.76"), conj.getTotal());
			}else if(conj.getNomeDespesa().equalsIgnoreCase("Telefone")){
				assertEquals(new BigDecimal("663.6"), conj.getTotal());
			}
		}
		
		//Conferindo os totaisMenais
		for(int i = 0; i < 12; i++){
			assertEquals(new BigDecimal("200.01"), tabela.getTotais().get(i));
		}
		
		//Conferindo o total geral
		assertEquals(new BigDecimal("2400.12"), tabela.getTotais().get(tabela.getTotais().size()-1));
	}
	
	@Test
	public void testGeraTabelaOrcamentoAnualComDespesasFixasDeMesesVariados() {
		int year = LocalDate.now().getYear();
		Set<FixedExpense> despesas = new HashSet<FixedExpense>();
		FixedExpense agua = new FixedExpense("Água", new BigDecimal("45.98"), new LocalDate(year, 1, 10));
		FixedExpense luz = new FixedExpense("Luz", new BigDecimal("98.73"), new LocalDate(year, 5, 12));
		FixedExpense telefone = new FixedExpense("Telefone", new BigDecimal("55.3"), new LocalDate(year, 9, 20));
		despesas.add(agua);
		despesas.add(luz);
		despesas.add(telefone);
		Budget<FixedExpense> orcamento = new BudgetBuilder<FixedExpense>(despesas).annual().withPrediction().build();
		
		AnualExpensesBudgetViewModel<FixedExpense> tabela = new AnualExpensesBudgetViewModel<FixedExpense>(orcamento);
		
		//Conferindo as despesasFixas planejadas
		for (ConjuntoDespesas conj : tabela.getDespesasAnuais()) {
			if(conj.getNomeDespesa().equalsIgnoreCase("Água")){
				assertEquals(12, conj.getDespesas().size(), 12);
			}else if(conj.getNomeDespesa().equalsIgnoreCase("Luz")){
				assertEquals(7, conj.getDespesas().size(), 7);
			}else if(conj.getNomeDespesa().equalsIgnoreCase("Telefone")){
				assertEquals(4, conj.getDespesas().size(), 4);
			}
		}
		
		//Conferindo os totais dos conjuntos
		for (ConjuntoDespesas conj : tabela.getDespesasAnuais()) {
			if(conj.getNomeDespesa().equalsIgnoreCase("Água")){
				assertEquals(new BigDecimal("551.76"), conj.getTotal());
			}else if(conj.getNomeDespesa().equalsIgnoreCase("Luz")){
				assertEquals(new BigDecimal("789.84"), conj.getTotal());
			}else if(conj.getNomeDespesa().equalsIgnoreCase("Telefone")){
				assertEquals(new BigDecimal("221.2"), conj.getTotal());
			}
		}
		
		//Conferindo os totaisMenais
		for(int i = 0; i < 12; i++){
			if(i >= 0 && i< 4){
				assertEquals(new BigDecimal("45.98"), tabela.getTotais().get(i));
			}else if(i > 3 && i< 8){
				assertEquals(new BigDecimal("144.71"), tabela.getTotais().get(i));
			}else{
				assertEquals(new BigDecimal("200.01"), tabela.getTotais().get(i));
			}
		}
				
		//Conferindo o total geral
		assertEquals(new BigDecimal("1562.80"), tabela.getTotais().get(tabela.getTotais().size()-1));
	}

}
