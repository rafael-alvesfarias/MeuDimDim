package br.com.mdd.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name = "entry")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)	
public abstract class Entry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column
	private Integer id;

	@Column
	private String name;

	@Column
	private BigDecimal value;

	@Column
	private LocalDate dueDate;

	Entry() {
		// this constructor shoudn't be used
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
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
