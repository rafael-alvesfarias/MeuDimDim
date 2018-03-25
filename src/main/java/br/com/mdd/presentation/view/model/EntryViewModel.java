package br.com.mdd.presentation.view.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import br.com.mdd.domain.model.Entry;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.Income;
import br.com.mdd.domain.model.Investment;

public class EntryViewModel {
	
	private Integer id;
	
	private String name;
	
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal value;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dueDate;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date entryDate;
	
	private String type;
	
	private boolean posted;
	
	private Integer account;
	
	private Collection<AccountViewModel> accounts;
	
	public static final EntryViewModel fromEntry(Entry entry) {
		EntryViewModel e = new EntryViewModel();
		if (entry.getDueDate() != null) {
			e.setDueDate(entry.getDueDate().toDate());
		}
		e.setName(entry.getName());
		e.setId(entry.getId());
		e.setValue(entry.getValue());
		if (entry.getEntryDate() != null) {
			e.setEntryDate(entry.getEntryDate().toDate());
		}
		e.setPosted(entry.isPosted());
		if (entry.getAccount() != null) {
			e.setAccount(entry.getAccount().getId());
		}
		if (entry instanceof Expense) {
			e.setType("despesa");
		} else if (entry instanceof Income) {
			e.setType("receita");
		} else if (entry instanceof Investment) {
			e.setType("investimento");
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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public boolean isPosted() {
		return posted;
	}

	public void setPosted(boolean posted) {
		this.posted = posted;
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
