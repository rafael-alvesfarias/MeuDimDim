package br.com.mdd.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

/**
 * Um orçamento é um conjunto de lançamentos realizados em um determinado período. 
 * @author rafaelfarias
 *
 */
public class Budget<E extends Entry> {
	
	private Set<E> entries;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private boolean predictEntries = false;
	
	public Budget(Set<E> entries){
		if(entries == null){
			throw new IllegalArgumentException("Conjunto de despesas não pode ser nulo!");
		}
		this.entries = new HashSet<E>(entries);
	}

	public Budget<E> monthly() {
		LocalDate hoje = LocalDate.now();
		this.dateFrom = hoje.withDayOfMonth(hoje.dayOfMonth().getMinimumValue());
		this.dateTo = hoje.withDayOfMonth(hoje.dayOfMonth().getMaximumValue());
		return this;
	}
	
	public Budget<E> annual() {
		LocalDate hoje = LocalDate.now();
		this.dateFrom = hoje.withMonthOfYear(1).withDayOfMonth(hoje.dayOfMonth().getMinimumValue());
		this.dateTo = hoje.withMonthOfYear(12).withDayOfMonth(hoje.dayOfMonth().getMaximumValue());
		
		return this;		
	}
	
	public Budget<E> withPeriod(LocalDate dataDe, LocalDate dataAte){
		if(dataDe == null || dataAte == null){
			throw new IllegalArgumentException("Período passado por parâmetro não pode ser nulo: dataDe="+ dataDe + "dataAte=" + dataAte);
		}
		
		if(dataDe.isAfter(dataAte)){
			throw new IllegalArgumentException("Período passado por parâmetro é inválido: dataDe="+ dataDe + "dataAte=" + dataAte);
		}
		
		this.dateFrom = dataDe;
		this.dateTo = dataAte;
		return this;
	}
	
	public Budget<E> withPrediction(){
		this.predictEntries = true;		
		return this;
	}
	
	private void generatePredictedEntries() throws InstantiationException, IllegalAccessException{
		Set<E> predictedEntries = new HashSet<E>(this.entries);
		for (E entry : this.entries) {
			int mesDespesa = entry.getDueDate().getMonthOfYear();
			int ultimoMes = this.getDateTo().getMonthOfYear();
			for(int novoMes = mesDespesa + 1 ; novoMes <= ultimoMes; novoMes++){
				LocalDate novaData = entry.getDueDate().withMonthOfYear(novoMes);
				//FIXME Possível problema de performance
				@SuppressWarnings("unchecked")
				E predictedEntry = (E) entry.getClass().newInstance();
				predictedEntry.setId(entry.getId());
				predictedEntry.setName(entry.getName());
				predictedEntry.setValue(entry.getValue());
				predictedEntry.setDueDate(novaData);
				
				if(!predictedEntries.contains(predictedEntry)){
					predictedEntries.add((E) predictedEntry);
				}
			}
		}
		this.entries = predictedEntries;
	}

	public Budget<E> generate() {
		if(dateFrom == null || dateTo == null){
			entries = new HashSet<E>();
			return this;
		}
		
		if(predictEntries){
			try {
				this.generatePredictedEntries();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("Erro ao instanciar o tipo.", e);
			}
		}
		
		Set<E> budget = new HashSet<E>();
		for (Entry entry : entries) {
			if(isInsideInterval(entry.getDueDate())){
				budget.add((E) entry);
			}
		}
		this.entries = budget;
		return this;
	}
	
	private boolean isInsideInterval(LocalDate data){
		return dateFrom.equals(data) || (dateFrom.isBefore(data) && data.isBefore(dateTo)) || data.equals(dateTo);
	}

	public Set<E> getEntries() {
		return Collections.unmodifiableSet(entries);
	}

	public void setExpenses(Set<E> entries) {
		this.entries = new HashSet<E>(entries);
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}
	
}
