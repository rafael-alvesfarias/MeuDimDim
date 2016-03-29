package br.com.mdd.domain.model;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.Locale;

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
public abstract class Entry implements Comparable<Entry> {

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getDueDate() == null) ? 0 : this.getDueDate().hashCode());
		result = prime * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
		result = prime * result + ((this.getValue() == null) ? 0 : this.getValue().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Expense other = (Expense) obj;
		if (this.getDueDate() == null) {
			if (other.getDueDate() != null)
				return false;
		} else if (!this.getDueDate().equals(other.getDueDate()))
			return false;
		if (this.getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!this.getName().equals(other.getName()))
			return false;
		if (this.getValue() == null) {
			if (other.getValue() != null)
				return false;
		} else if (!this.getValue().equals(other.getValue()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Entry o) {
		final Collator collator = Collator.getInstance(new Locale("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);

		return collator.compare(this.getName(), o.getName());
	}

}
