package br.com.mdd.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import br.com.mdd.domain.model.DespesaFixa;

/**
 * Um or�amento � um conjunto de lan�amentos realizados em um determinado per�odo. 
 * @author rafaelfarias
 *
 */
public class Orcamento {
	
	private Set<Despesa> despesas;
	private LocalDate dataDe;
	private LocalDate dataAte;
	private boolean preverLancamentos = false;
	
	public Orcamento(Set<Despesa> despesasFixas){
		if(despesasFixas == null){
			throw new IllegalArgumentException("Conjunto de despesas não pode ser nulo!");
		}
		this.despesas = new HashSet<Despesa>(despesasFixas);
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
		if(dataDe == null || dataAte == null){
			throw new IllegalArgumentException("Período passado por parâmetro não pode ser nulo: dataDe="+ dataDe + "dataAte=" + dataAte);
		}
		
		if(dataDe.isAfter(dataAte)){
			throw new IllegalArgumentException("Período passado por parâmetro é inválido: dataDe="+ dataDe + "dataAte=" + dataAte);
		}
		
		this.dataDe = dataDe;
		this.dataAte = dataAte;
		return this;
	}
	
	public Orcamento comPrevisao(){
		this.preverLancamentos = true;		
		return this;
	}
	
	private void gerarLancamentosPrevistos(){
		Set<Despesa> despesaFixasPrevistas = new HashSet<Despesa>(this.despesas);
		for (Despesa despesaFixa : this.despesas) {
			int mesDespesa = despesaFixa.getDataLancamento().getMonthOfYear();
			int ultimoMes = this.getDataAte().getMonthOfYear();
			for(int novoMes = mesDespesa + 1; novoMes <= ultimoMes; novoMes++){
				LocalDate novaData = despesaFixa.getDataLancamento().withMonthOfYear(novoMes);
				DespesaFixa despesaFixaPrevista = new DespesaFixa(despesaFixa.getDescricao(), despesaFixa.getValor(), novaData);
				if(!despesaFixasPrevistas.contains(despesaFixaPrevista)){
					despesaFixasPrevistas.add(despesaFixaPrevista);
				}
			}
		}
		this.despesas = despesaFixasPrevistas;
	}

	public Orcamento gerar() {
		if(dataDe == null || dataAte == null){
			despesas = new HashSet<Despesa>();
			return this;
		}
		
		if(preverLancamentos){
			this.gerarLancamentosPrevistos();
		}
		
		Set<Despesa> orcamento = new HashSet<Despesa>();
		for (Despesa despesa : despesas) {
			if(estaDentroDoIntervalo(despesa.getDataLancamento())){
				orcamento.add(despesa);
			}
		}
		this.despesas = orcamento;
		return this;
	}
	
	private boolean estaDentroDoIntervalo(LocalDate data){
		return dataDe.equals(data) || (dataDe.isBefore(data) && data.isBefore(dataAte)) || data.equals(dataAte);
	}

	public Set<Despesa> getDespesas() {
		return Collections.unmodifiableSet(despesas);
	}

	public void setDespesas(Set<Despesa> despesasFixas) {
		this.despesas = new HashSet<Despesa>(despesasFixas);
	}

	public LocalDate getDataDe() {
		return dataDe;
	}

	public LocalDate getDataAte() {
		return dataAte;
	}
	
}
