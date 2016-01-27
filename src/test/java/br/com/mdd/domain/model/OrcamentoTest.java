package br.com.mdd.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class OrcamentoTest {
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGerarOrcamentoAnualDespesasFixas() {
		Set<Expense> despesasFixas = new HashSet<Expense>();
		FixedExpense agua = new FixedExpense("Água", new BigDecimal("45.98"), new LocalDate(2016, 1, 9));
		FixedExpense luz = new FixedExpense("Luz", new BigDecimal("98.73"), new LocalDate(2016, 2, 9));
		FixedExpense telefone = new FixedExpense("Telefone", new BigDecimal("55.3"), new LocalDate(2020, 1, 9));
		despesasFixas.add(agua);
		despesasFixas.add(luz);
		despesasFixas.add(telefone);
		FixedExpense aguaEmMaio = new FixedExpense(agua.getName(), agua.getValue(), agua.getMaturityDate().plusMonths(4));
		FixedExpense luzEmJaneiro = new FixedExpense(luz.getName(), luz.getValue(), luz.getMaturityDate().minusMonths(1));
		FixedExpense luzEmDezembro = new FixedExpense(luz.getName(), luz.getValue(), luz.getMaturityDate().plusMonths(10));
		
		Budget orcamentoAnual = new Budget(despesasFixas).anual().withPrediction().generate();
		
		assertEquals(23, orcamentoAnual.getExpenses().size());
		assertTrue(orcamentoAnual.getExpenses().contains(aguaEmMaio));
		assertTrue(orcamentoAnual.getExpenses().contains(luzEmDezembro));
		assertFalse(orcamentoAnual.getExpenses().contains(luzEmJaneiro));
		assertFalse(orcamentoAnual.getExpenses().contains(telefone));
		
	}
	
	@Test
	public void testGerarOrcamentoMensalDeDespesasFixas(){
		Set<Expense> despesasFixas = new HashSet<Expense>();
		FixedExpense agua = new FixedExpense("Água", new BigDecimal("45.98"), LocalDate.now());
		FixedExpense luz = new FixedExpense("Luz", new BigDecimal("98.73"), LocalDate.now().plusMonths(1));
		FixedExpense telefone = new FixedExpense("Telefone", new BigDecimal("55.3"), LocalDate.now().plusYears(5));
		despesasFixas.add(agua);
		despesasFixas.add(luz);
		despesasFixas.add(telefone);
		
		Budget orcamentoMensal = new Budget(despesasFixas).monthly().generate();
		
		assertEquals(1, orcamentoMensal.getExpenses().size());
		assertTrue(orcamentoMensal.getExpenses().contains(agua));
		assertFalse(orcamentoMensal.getExpenses().contains(luz));
		assertFalse(orcamentoMensal.getExpenses().contains(telefone));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCriaObjetoOrcamentoComConjuntoDeDespesasNulo(){
		new Budget(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOrcamentoComPeriodoNulo(){
		Set<Expense> despesas = new HashSet<Expense>();
		Budget orcamento = new Budget(despesas);
		
		try{
			orcamento.withPeriod(null, new LocalDate()).generate();
		}catch(IllegalArgumentException e){
			orcamento.withPeriod(new LocalDate(), null).generate();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOrcamentoComPeriodoInvalido(){
		Set<Expense> despesas = new HashSet<Expense>();
		Budget orcamento = new Budget(despesas);
		
		orcamento.withPeriod(LocalDate.now(), LocalDate.now().minusDays(1));
	}
	
	@Test
	public void testGerarOrcamentoSemInformarPeriodo(){
		Set<Expense> despesas = new HashSet<Expense>();
		despesas.add(new FixedExpense("teste", BigDecimal.ZERO, LocalDate.now()));
		Budget orcamento = new Budget(despesas);
		
		orcamento.generate();
		
		assertEquals(orcamento.getExpenses().size(), 0);
	}

}
