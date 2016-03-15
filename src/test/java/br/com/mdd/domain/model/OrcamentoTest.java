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
		Set<FixedExpense> despesasFixas = new HashSet<FixedExpense>();
		FixedExpense agua = new FixedExpense("Água", new BigDecimal("45.98"), new LocalDate(2016, 1, 9));
		FixedExpense luz = new FixedExpense("Luz", new BigDecimal("98.73"), new LocalDate(2016, 2, 9));
		FixedExpense telefone = new FixedExpense("Telefone", new BigDecimal("55.3"), new LocalDate(2020, 1, 9));
		despesasFixas.add(agua);
		despesasFixas.add(luz);
		despesasFixas.add(telefone);
		FixedExpense aguaEmMaio = new FixedExpense(agua.getName(), agua.getValue(), agua.getDueDate().plusMonths(4));
		FixedExpense luzEmJaneiro = new FixedExpense(luz.getName(), luz.getValue(), luz.getDueDate().minusMonths(1));
		FixedExpense luzEmDezembro = new FixedExpense(luz.getName(), luz.getValue(), luz.getDueDate().plusMonths(10));
		
		Budget<FixedExpense> orcamentoAnual = new Budget<FixedExpense>(despesasFixas).annual().withPrediction().generate();
		
		assertEquals(23, orcamentoAnual.getEntries().size());
		assertTrue(orcamentoAnual.getEntries().contains(aguaEmMaio));
		assertTrue(orcamentoAnual.getEntries().contains(luzEmDezembro));
		assertFalse(orcamentoAnual.getEntries().contains(luzEmJaneiro));
		assertFalse(orcamentoAnual.getEntries().contains(telefone));
		
	}
	
	@Test
	public void testGerarOrcamentoMensalDeDespesasFixas(){
		Set<FixedExpense> despesasFixas = new HashSet<FixedExpense>();
		FixedExpense agua = new FixedExpense("Água", new BigDecimal("45.98"), LocalDate.now());
		FixedExpense luz = new FixedExpense("Luz", new BigDecimal("98.73"), LocalDate.now().plusMonths(1));
		FixedExpense telefone = new FixedExpense("Telefone", new BigDecimal("55.3"), LocalDate.now().plusYears(5));
		despesasFixas.add(agua);
		despesasFixas.add(luz);
		despesasFixas.add(telefone);
		
		Budget<FixedExpense> orcamentoMensal = new Budget<FixedExpense>(despesasFixas).monthly().generate();
		
		assertEquals(1, orcamentoMensal.getEntries().size());
		assertTrue(orcamentoMensal.getEntries().contains(agua));
		assertFalse(orcamentoMensal.getEntries().contains(luz));
		assertFalse(orcamentoMensal.getEntries().contains(telefone));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCriaObjetoOrcamentoComConjuntoDeDespesasNulo(){
		new Budget<Expense>(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOrcamentoComPeriodoNulo(){
		Set<Expense> despesas = new HashSet<Expense>();
		Budget<Expense> orcamento = new Budget<Expense>(despesas);
		
		try{
			orcamento.withPeriod(null, new LocalDate()).generate();
		}catch(IllegalArgumentException e){
			orcamento.withPeriod(new LocalDate(), null).generate();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOrcamentoComPeriodoInvalido(){
		Set<Expense> despesas = new HashSet<Expense>();
		Budget<Expense> orcamento = new Budget<Expense>(despesas);
		
		orcamento.withPeriod(LocalDate.now(), LocalDate.now().minusDays(1));
	}
	
	@Test
	public void testGerarOrcamentoSemInformarPeriodo(){
		Set<Expense> despesas = new HashSet<Expense>();
		despesas.add(new FixedExpense("teste", BigDecimal.ZERO, LocalDate.now()));
		Budget<Expense> orcamento = new Budget<Expense>(despesas);
		
		orcamento.generate();
		
		assertEquals(orcamento.getEntries().size(), 0);
	}

}
