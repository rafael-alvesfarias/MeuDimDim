package br.com.mdd.domain.model;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class Entry {
	
	private String name;
	
	private BigDecimal value;
	
	private LocalDate dueDate;
	
	Entry() {
		//this constructor shoudn't be used
	}

	public Entry(String name, BigDecimal value, LocalDate dueDate) {
		this.name = name;
		this.value = value;
		this.dueDate = dueDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}	

}
