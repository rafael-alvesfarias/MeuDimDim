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
	
	Expense() {
		//Construtor necessário para framework
	}

	public Expense(String name, BigDecimal value, LocalDate maturityDate) {
		super(name, value, maturityDate, -1);
	}
	
	public void pay(Account a) {
		super.post(a);
	}
	
	public boolean isPaid() {
		return super.isPosted();
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

	@Override
	public Expense clone() {
		Expense e = (Expense) super.clone();
		if (category != null) {
			e.category = (Category) category.clone();
		}
		return e;
	}

}
