package br.com.mdd.domain;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import br.com.mdd.domain.DespesaFixa;
import br.com.mdd.domain.Orcamento;

public class OrcamentoTest {
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGerarOrcamentoAnualDespesasFixas() {
		Set<DespesaFixa> despesasFixas = new HashSet<DespesaFixa>();
		DespesaFixa agua = new DespesaFixa("Água", new BigDecimal("45.98"), new LocalDate(2015, 1, 9));
		DespesaFixa luz = new DespesaFixa("Luz", new BigDecimal("98.73"), new LocalDate(2015, 2, 9));
		DespesaFixa telefone = new DespesaFixa("Telefone", new BigDecimal("55.3"), new LocalDate(2020, 1, 9));
		despesasFixas.add(agua);
		despesasFixas.add(luz);
		despesasFixas.add(telefone);
		DespesaFixa aguaEmMaio = new DespesaFixa(agua.getDescricao(), agua.getValor(), agua.getDataVencimento().plusMonths(4));
		DespesaFixa luzEmJaneiro = new DespesaFixa(luz.getDescricao(), luz.getValor(), luz.getDataVencimento().minusMonths(1));
		DespesaFixa luzEmDezembro = new DespesaFixa(luz.getDescricao(), luz.getValor(), luz.getDataVencimento().plusMonths(10));
		
		Orcamento orcamentoAnual = new Orcamento(despesasFixas).anual().comPrevisao().gerar();
		
		assertEquals(23, orcamentoAnual.getDespesasFixas().size());
		assertTrue(orcamentoAnual.getDespesasFixas().contains(aguaEmMaio));
		assertTrue(orcamentoAnual.getDespesasFixas().contains(luzEmDezembro));
		assertFalse(orcamentoAnual.getDespesasFixas().contains(luzEmJaneiro));
		assertFalse(orcamentoAnual.getDespesasFixas().contains(telefone));
		
	}
	
	@Test
	public void testGerarOrcamentoMensalDeDespesasFixas(){
		Set<DespesaFixa> despesasFixas = new HashSet<DespesaFixa>();
		DespesaFixa agua = new DespesaFixa("Água", new BigDecimal("45.98"), LocalDate.now());
		DespesaFixa luz = new DespesaFixa("Luz", new BigDecimal("98.73"), LocalDate.now().plusMonths(1));
		DespesaFixa telefone = new DespesaFixa("Telefone", new BigDecimal("55.3"), LocalDate.now().plusYears(5));
		despesasFixas.add(agua);
		despesasFixas.add(luz);
		despesasFixas.add(telefone);
		
		Orcamento orcamentoMensal = new Orcamento(despesasFixas).mensal().gerar();
		
		assertEquals(1, orcamentoMensal.getDespesasFixas().size());
		assertTrue(orcamentoMensal.getDespesasFixas().contains(agua));
		assertFalse(orcamentoMensal.getDespesasFixas().contains(luz));
		assertFalse(orcamentoMensal.getDespesasFixas().contains(telefone));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCriaObjetoOrcamentoComConjuntoDeDespesasNulo(){
		new Orcamento(null);
	}

}
