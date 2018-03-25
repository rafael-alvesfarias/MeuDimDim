package br.com.mdd.presentation.view.model.expense;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.presentation.view.model.AccountViewModel;

public class ExpenseViewModel {
	
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
	
	private boolean fixedExpense;
	
	private boolean paid;
	
	private Collection<Category> categories;
	
	private Collection<AccountViewModel> accounts;
	
	private String error;
	
	public static final ExpenseViewModel fromExpense(Expense expense) {
		ExpenseViewModel e = new ExpenseViewModel();
		if (expense.getCategory() != null) {
			e.setCategory(expense.getCategory().getId());
		}
		e.setDueDate(expense.getDueDate().toDate());
		if (expense.getEntryDate() != null) {
			e.setEntryDate(expense.getEntryDate().toDate());
		}
		e.setName(expense.getName());
		e.setId(expense.getId());
		
		e.setValue(expense.getValue());
		if (expense.getAccount() != null) {
			e.setAccount(expense.getAccount().getId());
		}
		
		e.setPaid(expense.isPaid());
		
		if(expense instanceof FixedExpense) {
			e.setFixedExpense(true);			
		} else {
			e.setFixedExpense(false);
		}
		
		return e;
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

	public boolean isFixedExpense() {
		return fixedExpense;
	}

	public void setFixedExpense(boolean fixedExpense) {
		this.fixedExpense = fixedExpense;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
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
	
	public Date getEntryDate() {
		return entryDate;
	}
	
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
}
