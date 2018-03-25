package br.com.mdd.domain.model;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name="investment")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="investment_id"))
})
public class Investment extends Entry {
	
	private BigDecimal returnRate;
	
	private LocalDate withdrawlDate;
	
	private BigDecimal taxRate;
	
	
	Investment() {
		
	}
	
	public Investment(String name, BigDecimal value, LocalDate dueDate, BigDecimal returnRate, LocalDate withdrawlDate, BigDecimal taxRate) {
		super(name, value, dueDate, 1);
		this.returnRate = returnRate;
		this.withdrawlDate = withdrawlDate;
		this.taxRate = taxRate;
	}
	
	public Investment(String name, BigDecimal value, LocalDate dueDate, BigDecimal returnRate) {
		this(name, value, dueDate, returnRate, null, BigDecimal.ZERO);
	}

	public BigDecimal getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(BigDecimal returnRate) {
		this.returnRate = returnRate;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public LocalDate getWithdrawlDate() {
		return withdrawlDate;
	}

	public void setWithdrawlDate(LocalDate withdrawlDate) {
		this.withdrawlDate = withdrawlDate;
	}

}
