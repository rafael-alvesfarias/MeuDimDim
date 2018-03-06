package br.com.mdd.domain.model;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;

/**
 * Um orçamento é um conjunto de lançamentos realizados em um determinado período. 
 * @author rafaelfarias
 *
 */
public final class Budget<E extends Entry> {
	
	private final String name;
	private Set<E> entries;
	private final LocalDate dateFrom;
	private final LocalDate dateTo;
	private BigDecimal total = BigDecimal.ZERO;
	private final boolean predictEntries;
	
	public Budget(String name, Set<E> entries, LocalDate dateFrom, LocalDate dateTo, boolean predictEntries) {
		if (name == null){
			throw new IllegalArgumentException("Nome do orçamento não pode ser nulo!");
		}
		if (entries == null){
			throw new IllegalArgumentException("Conjunto de despesas não pode ser nulo!");
		}
		if(dateFrom == null || dateTo == null){
			throw new IllegalArgumentException("Período passado por parâmetro não pode ser nulo: dataDe="+ dateFrom + "dataAte=" + dateTo);
		}
		if(dateFrom.isAfter(dateTo)){
			throw new IllegalArgumentException("Período passado por parâmetro é inválido: dataDe="+ dateFrom + "dataAte=" + dateTo);
		}
		this.name = name;
		this.entries = entries;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.predictEntries = predictEntries;	
		this.generate();
	}
	
	public Budget(String name, Set<E> entries, LocalDate dateFrom, LocalDate dateTo) {
		this(name, entries, dateFrom, dateTo, false);
	}
	
	public Budget<E> subBudget(String name, LocalDate dateFrom, LocalDate dateTo) {
		return new Budget<E>(name, entries, dateFrom, dateTo, predictEntries);
	}
	
	public Budget<E> subBudget(String name, Month month) {
		LocalDate dateFrom = this.dateFrom.withMonthOfYear(month.getValue());
		LocalDate dateTo = dateFrom.withDayOfMonth(dateFrom.dayOfMonth().getMaximumValue());
		return this.subBudget(name, dateFrom, dateTo);
	}
	
	public Budget<E> subBudget(String name, String entryName) {
		if (entryName == null) {
			return this;
		} else {
			//TODO Talvez o filtro por lançamento possa ser feito no método generate
			Set<E> newEntries = this.entries.stream()
					.filter(entry -> entryName.equals(entry.getName()))
					.collect(Collectors.toSet());
			return new Budget<E>(name, newEntries, dateFrom, dateTo, predictEntries);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void generatePredictedEntries() {
		Set<E> predictedEntries = new HashSet<E>(this.entries);
		for (E entry : this.entries) {
			int mesDespesa = entry.getDueDate().getMonthOfYear();
			int ultimoMes = this.getDateTo().getMonthOfYear();
			for(int novoMes = mesDespesa + 1 ; novoMes <= ultimoMes; novoMes++){
				LocalDate novaData = entry.getDueDate().withMonthOfYear(novoMes);
				E predictedEntry = (E) entry.clone();
				predictedEntry.setId(entry.getId());
				predictedEntry.setName(entry.getName());
				predictedEntry.setValue(entry.getValue());
				predictedEntry.setDueDate(novaData);
				
				if(!predictedEntries.contains(predictedEntry)){
					predictedEntries.add(predictedEntry);
				}
			}
		}
		this.entries = predictedEntries;
	}

	private Budget<E> generate() {
		if (dateFrom == null || dateTo == null){
			entries = new HashSet<E>();
			return this;
		}
		
		if (predictEntries) {
			this.generatePredictedEntries();
		}
		
		Set<E> budget = new HashSet<E>();
		for (E entry : entries) {
			if (isInsideInterval(entry.getDueDate())) {
				budget.add((E) entry);
				total = total.add(entry.getValue());
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

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public BigDecimal getTotal() {
		return total;
	}
	
	public String getName() {
		return name;
	}
}
