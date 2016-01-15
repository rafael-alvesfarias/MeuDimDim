package br.com.mdd.web.presentation.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import br.com.mdd.domain.model.Despesa;
import br.com.mdd.domain.model.DespesaFixa;
import br.com.mdd.domain.model.Orcamento;
import br.com.mdd.presentation.model.OrcamentoDespesasAnuaisViewModel;
import br.com.mdd.presentation.model.OrcamentoDespesasAnuaisViewModel.ConjuntoDespesas;

public class OrcamentoDespesasAnuaisViewModelTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGeraTabelaOrcamentoAnualComDespesasFixasDeJaneiro() {
		Set<Despesa> despesas = new HashSet<Despesa>();
		DespesaFixa agua = new DespesaFixa("Água", new BigDecimal("45.98"), new LocalDate(2016, 1, 10));
		DespesaFixa luz = new DespesaFixa("Luz", new BigDecimal("98.73"), new LocalDate(2016, 1, 12));
		DespesaFixa telefone = new DespesaFixa("Telefone", new BigDecimal("55.3"), new LocalDate(2016, 1, 20));
		despesas.add(agua);
		despesas.add(luz);
		despesas.add(telefone);
		Orcamento orcamento = new Orcamento(despesas).anual().comPrevisao().gerar();
		
		OrcamentoDespesasAnuaisViewModel tabela = new OrcamentoDespesasAnuaisViewModel(orcamento);
		
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
		Set<Despesa> despesas = new HashSet<Despesa>();
		DespesaFixa agua = new DespesaFixa("Água", new BigDecimal("45.98"), new LocalDate(2016, 1, 10));
		DespesaFixa luz = new DespesaFixa("Luz", new BigDecimal("98.73"), new LocalDate(2016, 5, 12));
		DespesaFixa telefone = new DespesaFixa("Telefone", new BigDecimal("55.3"), new LocalDate(2016, 9, 20));
		despesas.add(agua);
		despesas.add(luz);
		despesas.add(telefone);
		Orcamento orcamento = new Orcamento(despesas).anual().comPrevisao().gerar();
		
		OrcamentoDespesasAnuaisViewModel tabela = new OrcamentoDespesasAnuaisViewModel(orcamento);
		
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
