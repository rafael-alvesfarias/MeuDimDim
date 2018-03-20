package br.com.mdd.presentation.view.model;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import br.com.mdd.domain.model.Account;
import br.com.mdd.domain.model.AccountType;

public class AccountViewModel {
	
	private Integer id;
	
	private String institutionName;
	
	private Integer number;
	
	private AccountType type;
	
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal balance;
	
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal startBalance;
	
	public AccountViewModel() {
		this.startBalance = this.balance = BigDecimal.ZERO;
	}
	
	public static AccountViewModel fromAccount(Account account) {
		AccountViewModel a = new AccountViewModel();
		a.setId(account.getId());
		a.setInstitutionName(account.getInstitutionName());
		a.setType(account.getType());
		a.setBalance(account.getBalance());
		
		return a;
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

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public AccountType[] getTypes() {
		return AccountType.values();
	}
}
