package br.com.mdd.domain;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import br.com.mdd.domain.DespesaFixa;
import br.com.mdd.domain.Orcamento;

public class OrcamentoTest {
	
	private Orcamento orcamento;
	private Set<DespesaFixa> despesasIniciais;

	@Before
	public void setUp() throws Exception {
		despesasIniciais = new TreeSet<DespesaFixa>();
		DespesaFixa agua = new DespesaFixa("Água", new BigDecimal("45.98"), 10);
		DespesaFixa luz = new DespesaFixa("Luz", new BigDecimal("98.73"), 12);
		DespesaFixa telefone = new DespesaFixa("Telefone", new BigDecimal("55.3"), 30);
		despesasIniciais.add(agua);
		despesasIniciais.add(luz);
		despesasIniciais.add(telefone);
		
		orcamento = new Orcamento(despesasIniciais);
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
		
		Orcamento orcamentoAnual = new Orcamento(despesasFixas).anual().gerar();
		
		assertEquals(23, orcamentoAnual.size());
		assertTrue(orcamentoAnual.contains(aguaEmMaio));
		assertTrue(orcamentoAnual.contains(luzEmDezembro));
		assertFalse(orcamentoAnual.contains(luzEmJaneiro));
		assertFalse(orcamentoAnual.contains(telefone));
		
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
		
		assertEquals(1, orcamentoMensal.size());
		assertTrue(orcamentoMensal.contains(agua));
		assertFalse(orcamentoMensal.contains(luz));
		assertFalse(orcamentoMensal.contains(telefone));
	}
	
	@Test
	public void testObterTotalDeDespesasAnuaisDeDeterminadaDespesaFixa(){
		BigDecimal resultadoEsperado = new BigDecimal("551.76");
		BigDecimal total = BigDecimal.ZERO;
		String despesaDesejada = "Água";
		Map<String, List<DespesaFixa>> obj = orcamento.gerarOrcamentoDoAno();
		
		List<DespesaFixa> despesasDeAguaNoAno = obj.get(despesaDesejada);
		for (DespesaFixa d : despesasDeAguaNoAno) {
			total = total.add(d.getValor());
		}
		
		assertEquals(resultadoEsperado, total);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCriaObjetoOrcamentoComConjuntoDeDespesasNulo(){
		new Orcamento(null);
	}

}
