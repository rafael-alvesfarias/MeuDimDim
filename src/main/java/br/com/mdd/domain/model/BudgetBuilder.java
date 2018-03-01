package br.com.mdd.domain.model;

import java.util.Set;

import org.joda.time.LocalDate;

public class BudgetBuilder<E extends Entry> {

	private String name;

	private Set<E> entries;

	private LocalDate dateFrom;

	private LocalDate dateTo;

	private boolean predictEntries;

	public BudgetBuilder(Set<E> entries) {
		this.name = "Generic Budget";
		this.entries = entries;
		this.predictEntries = false;
	}

	public Budget<E> build() {
		return new Budget<E>(name, entries, dateFrom, dateTo, predictEntries);
	}
	
	public BudgetBuilder<E> withName(String name) {
		if (name != null) {
			this.name = name;
		}
		return this;
	}

	public BudgetBuilder<E> monthly() {
		LocalDate hoje = LocalDate.now();
		this.dateFrom = hoje.withDayOfMonth(hoje.dayOfMonth().getMinimumValue());
		this.dateTo = hoje.withDayOfMonth(hoje.dayOfMonth().getMaximumValue());
		return this;
	}

	public BudgetBuilder<E> monthly(int month) {
		LocalDate hoje = LocalDate.now();
		this.dateFrom = hoje.withMonthOfYear(month).withDayOfMonth(hoje.dayOfMonth().getMinimumValue());
		this.dateTo = hoje.withDayOfMonth(hoje.dayOfMonth().getMaximumValue());
		return this;
	}

	public BudgetBuilder<E> annual() {
		LocalDate hoje = LocalDate.now();
		this.dateFrom = new LocalDate(hoje.getYear(), 1, 1);
		this.dateTo = new LocalDate(hoje.getYear(), 12, 31);

		return this;
	}

	public BudgetBuilder<E> withPeriod(LocalDate dataDe, LocalDate dataAte) {
		if (dataDe == null || dataAte == null) {
			throw new IllegalArgumentException(
					"Período passado por parâmetro não pode ser nulo: dataDe=" + dataDe + "dataAte=" + dataAte);
		}

		if (dataDe.isAfter(dataAte)) {
			throw new IllegalArgumentException(
					"Período passado por parâmetro é inválido: dataDe=" + dataDe + "dataAte=" + dataAte);
		}

		this.dateFrom = dataDe;
		this.dateTo = dataAte;
		return this;
	}

	public BudgetBuilder<E> withPrediction() {
		this.predictEntries = true;
		return this;
	}

}
