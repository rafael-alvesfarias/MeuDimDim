package br.com.mdd.domain.model;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.Locale;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name="expense")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="expense_id"))
})
public abstract class Expense extends Entry implements Comparable<Expense> {
	
	@ManyToOne
	private Category category;
	
	@Column
	private Boolean paid;
	
	@ManyToOne
	private User user;
	
	Expense() {
		//Construtor necessário para framework
	}

	public Expense(String name, BigDecimal value, LocalDate maturityDate) {
		this.setName(name);
		this.setValue(value);
		this.setDueDate(maturityDate);
	}

	public Expense(String descricao, BigDecimal valor) {
		this(descricao, valor, LocalDate.now());
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.getDueDate() == null) ? 0 : this.getDueDate().hashCode());
		result = prime * result
				+ ((this.getName() == null) ? 0 : this.getName().hashCode());
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
	public int compareTo(Expense o) {
		final Collator collator = Collator.getInstance(new Locale("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);
		
		return collator.compare(this.getName(), o.getName());
	}

}
