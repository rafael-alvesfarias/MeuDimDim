package br.com.mdd.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.joda.time.LocalDate;

/**
 * Um orçamento é um conjunto de lançamentos realizados em um determinado período. 
 * @author rafaelfarias
 *
 */
public class Orcamento implements Iterable<DespesaFixa> {
	
	protected Set<DespesaFixa> despesasFixas;
	private LocalDate dataDe;
	private LocalDate dataAte;
	
	public Orcamento(Set<DespesaFixa> despesasFixas){
		if(despesasFixas == null){
			throw new IllegalArgumentException("Conjunto de despesas não pode ser nulo!");
		}
		this.despesasFixas = despesasFixas;
	}

	public Map<String, List<DespesaFixa>> gerarOrcamentoDoAno() {
		Map<String, List<DespesaFixa>> retorno = new TreeMap<String, List<DespesaFixa>>();
		for (DespesaFixa despesa : despesasFixas) {
			List<DespesaFixa> despesasAnuais = new ArrayList<DespesaFixa>(12);
			
			for(int mes = 1; mes <= 12; mes++){
				LocalDate data = despesa.getDataVencimento().withDayOfMonth(despesa.getVencimento()).withMonthOfYear(Integer.valueOf(mes));
				despesasAnuais.add(new DespesaFixa(despesa.getDescricao(), despesa.getValor(), data));
			}
			
			retorno.put(despesa.getDescricao(), despesasAnuais);
		}

		return retorno;
	}

	public Orcamento mensal() {
		LocalDate hoje = LocalDate.now();
		this.dataDe = hoje.withDayOfMonth(hoje.dayOfMonth().getMinimumValue());
		this.dataAte = hoje.withDayOfMonth(hoje.dayOfMonth().getMaximumValue());
		return this;
	}
	
	public Orcamento anual() {
		Orcamento orcamentoAnual = new Orcamento(this.despesasFixas){
			@Override
			public Orcamento gerar() {
				Set<DespesaFixa> despesaFixasPrevistas = new HashSet<DespesaFixa>(this.despesasFixas);
				for (DespesaFixa despesaFixa : this) {
					int mesDespesa = despesaFixa.getDataVencimento().getMonthOfYear();
					for(int novoMes = mesDespesa + 1; novoMes <= 12; novoMes++){
						LocalDate novaData = despesaFixa.getDataVencimento().withMonthOfYear(novoMes);
						DespesaFixa despesaFixaPrevista = new DespesaFixa(despesaFixa.getDescricao(), despesaFixa.getValor(), novaData);
						if(!despesaFixasPrevistas.contains(despesaFixaPrevista)){
							despesaFixasPrevistas.add(despesaFixaPrevista);
						}
					}
				}
				this.despesasFixas = despesaFixasPrevistas;
				return super.gerar();
			}
		};
		
		LocalDate hoje = LocalDate.now();
		orcamentoAnual.dataDe = hoje.withMonthOfYear(1).withDayOfMonth(hoje.dayOfMonth().getMinimumValue());
		orcamentoAnual.dataAte = hoje.withMonthOfYear(12).withDayOfMonth(hoje.dayOfMonth().getMaximumValue());
		
		return orcamentoAnual;		
	}
	
	public Orcamento porPeriodo(LocalDate dataDe, LocalDate dataAte){
		return this;
	}

	public Orcamento gerar() {
		Set<DespesaFixa> orcamento = new HashSet<DespesaFixa>();
		for (DespesaFixa despesa : despesasFixas) {
			if(estaDentroDoIntervalo(despesa.getDataVencimento())){
				orcamento.add(despesa);
			}
		}
		this.despesasFixas = orcamento;
		return this;
	}

	@Override
	public Iterator<DespesaFixa> iterator() {
		return this.despesasFixas.iterator();
	}
	
	public boolean contains(DespesaFixa despesa){
		return this.despesasFixas.contains(despesa);
	}
	
	public int size(){
		return this.despesasFixas.size();
	}
	
	private boolean estaDentroDoIntervalo(LocalDate data){
		return dataDe.equals(data) || (dataDe.isBefore(data) && data.isBefore(dataAte)) || data.equals(dataAte);
	}
	
	protected void add(DespesaFixa despesaFixa){
		despesasFixas.add(despesaFixa);
	}
	
}
