package br.com.mdd.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String name;
	
	private Integer number;
	
	private BigDecimal balance;
	
	private AccountType type;
	
	public void deposit(BigDecimal amount) {
		this.balance.add(balance);
	}
	
	public void withdraw(BigDecimal amount) {
		this.balance.subtract(amount);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	protected void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}
}
