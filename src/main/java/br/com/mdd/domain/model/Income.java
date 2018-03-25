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
@Table(name = "income")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="income_id"))
})
public class Income extends Entry{
	
	@ManyToOne
	private Category category;
	
	public Income(String name, BigDecimal value, LocalDate dueDate) {
		super(name, value, dueDate, 1);
	}
	
	Income() {
		
	}
	
	public void receive(Account a) {
		super.post(a);
	}
	
	public boolean isReceived() {
		return super.isPosted();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
