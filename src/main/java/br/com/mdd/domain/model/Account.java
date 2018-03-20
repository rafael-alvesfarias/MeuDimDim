package br.com.mdd.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String institutionName;
	
	private Integer number;
	
	private BigDecimal balance;
	
	@Column
	@Enumerated(EnumType.STRING)
	private AccountType type;
	
	@ManyToOne
	private User user;
	
	protected Account() {
		this.balance = BigDecimal.ZERO;
	}
	
	public Account(String institutionName, Integer number, BigDecimal balance, AccountType type) {
		super();
		this.institutionName = institutionName;
		this.number = number;
		this.balance = balance;
		this.type = type;
	}

	public Account(String institutionName, Integer number, AccountType type) {
		super();
		this.institutionName = institutionName;
		this.number = number;
		this.balance = BigDecimal.ZERO;
		this.type = type;
	}


	public void deposit(BigDecimal amount) {
		this.balance.add(balance);
	}
	
	public void withdraw(BigDecimal amount) {
		this.balance.subtract(amount);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
