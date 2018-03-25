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
	
	private BigDecimal balance;
	
	@Column
	@Enumerated(EnumType.STRING)
	private AccountType type;
	
	@ManyToOne
	private User user;
	
	protected Account() {
		this.balance = BigDecimal.ZERO;
	}
	
	public Account(String institutionName, BigDecimal balance, AccountType type) {
		super();
		this.institutionName = institutionName;
		this.balance = balance;
		this.type = type;
	}

	public Account(String institutionName, AccountType type) {
		super();
		this.institutionName = institutionName;
		this.balance = BigDecimal.ZERO;
		this.type = type;
	}
	
	public void deposit(BigDecimal value) {
		this.balance = this.balance.add(value);
	}
	
	public void withdraw(BigDecimal value) {
		this.balance = this.balance.subtract(value);
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
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
