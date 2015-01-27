package br.com.mdd.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

/**
 * Um or�aamento � um conjunto de lan�amentos realizados em um determinado per�odo. 
 * @author rafaelfarias
 *
 */
public class Orcamento {
	
	private Set<DespesaFixa> despesasFixas;
	private LocalDate dataDe;
	private LocalDate dataAte;
	private boolean preverLancamentos = false;
	
	public Orcamento(Set<DespesaFixa> despesasFixas){
		if(despesasFixas == null){
			throw new IllegalArgumentException("Conjunto de despesas não pode ser nulo!");
		}
		this.despesasFixas = new HashSet<DespesaFixa>(despesasFixas);
	}

	public Orcamento mensal() {
		LocalDate hoje = LocalDate.now();
		this.dataDe = hoje.withDayOfMonth(hoje.dayOfMonth().getMinimumValue());
		this.dataAte = hoje.withDayOfMonth(hoje.dayOfMonth().getMaximumValue());
		return this;
	}
	
	public Orcamento anual() {
		LocalDate hoje = LocalDate.now();
		this.dataDe = hoje.withMonthOfYear(1).withDayOfMonth(hoje.dayOfMonth().getMinimumValue());
		this.dataAte = hoje.withMonthOfYear(12).withDayOfMonth(hoje.dayOfMonth().getMaximumValue());
		
		return this;		
	}
	
	public Orcamento porPeriodo(LocalDate dataDe, LocalDate dataAte){
		return this;
	}
	
	public Orcamento comPrevisao(){
		this.preverLancamentos = true;		
		return this;
	}
	
	private void gerarLancamentosPrevistos(){
		Set<DespesaFixa> despesaFixasPrevistas = new HashSet<DespesaFixa>(this.despesasFixas);
		for (DespesaFixa despesaFixa : this.despesasFixas) {
			int mesDespesa = despesaFixa.getDataVencimento().getMonthOfYear();
			int ultimoMes = this.getDataAte().getMonthOfYear();
			for(int novoMes = mesDespesa + 1; novoMes <= ultimoMes; novoMes++){
				LocalDate novaData = despesaFixa.getDataVencimento().withMonthOfYear(novoMes);
				DespesaFixa despesaFixaPrevista = new DespesaFixa(despesaFixa.getDescricao(), despesaFixa.getValor(), novaData);
				if(!despesaFixasPrevistas.contains(despesaFixaPrevista)){
					despesaFixasPrevistas.add(despesaFixaPrevista);
				}
			}
		}
		this.despesasFixas = despesaFixasPrevistas;
	}

	public Orcamento gerar() {
		if(preverLancamentos){
			this.gerarLancamentosPrevistos();
		}
		
		Set<DespesaFixa> orcamento = new HashSet<DespesaFixa>();
		for (DespesaFixa despesa : despesasFixas) {
			if(estaDentroDoIntervalo(despesa.getDataVencimento())){
				orcamento.add(despesa);
			}
		}
		this.despesasFixas = orcamento;
		return this;
	}
	
	private boolean estaDentroDoIntervalo(LocalDate data){
		return dataDe.equals(data) || (dataDe.isBefore(data) && data.isBefore(dataAte)) || data.equals(dataAte);
	}

	public Set<DespesaFixa> getDespesasFixas() {
		return Collections.unmodifiableSet(despesasFixas);
	}

	public void setDespesasFixas(Set<DespesaFixa> despesasFixas) {
		this.despesasFixas = new HashSet<DespesaFixa>(despesasFixas);
	}

	public LocalDate getDataDe() {
		return dataDe;
	}

	public void setDataDe(LocalDate dataDe) {
		this.dataDe = dataDe;
	}

	public LocalDate getDataAte() {
		return dataAte;
	}

	public void setDataAte(LocalDate dataAte) {
		this.dataAte = dataAte;
	}
	
}
