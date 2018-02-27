package br.com.mdd.domain.model;

import java.math.BigDecimal;

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
public abstract class Expense extends Entry {
	
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
		super(name, value, maturityDate);
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
	public Expense clone() {
		Expense e = (Expense) super.clone();
		e.category = (Category) category.clone();
		return e;
	}

}
