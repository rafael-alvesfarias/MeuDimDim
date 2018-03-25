package br.com.mdd.presentation.view.model.income;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Income;
import br.com.mdd.presentation.view.model.AccountViewModel;

public class IncomeViewModel {
	
	private Integer id;
	
	private String name;
	
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal value;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dueDate;
	
	@DateTimeFormat(pattern = "dd/MMyyyy")
	private Date entryDate;
	
	private Integer category;
	
	private Integer account;
	
	private boolean received;
	
	private Collection<Category> categories;
	
	private Collection<AccountViewModel> accounts;
	
	public static final IncomeViewModel fromIncome(Income income) {
		IncomeViewModel i = new IncomeViewModel();
		if (income.getCategory() != null) {
			i.setCategory(income.getCategory().getId());
		}
		i.setDueDate(income.getDueDate().toDate());
		if (income.getEntryDate() != null) {
			i.setEntryDate(income.getEntryDate().toDate());
		}
		i.setName(income.getName());
		i.setId(income.getId());
		i.setReceived(income.isReceived());
		i.setValue(income.getValue());
		if (income.getAccount() != null) {
			i.setAccount(income.getAccount().getId());
		}
		
		return i;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}
	
	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public BigDecimal getValue() {
		return value;
	}
	
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public Collection<AccountViewModel> getAccounts() {
		return accounts;
	}

	public void setAccounts(Collection<AccountViewModel> accounts) {
		this.accounts = accounts;
	}
	
	
}
