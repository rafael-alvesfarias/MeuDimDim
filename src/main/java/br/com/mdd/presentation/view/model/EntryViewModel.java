package br.com.mdd.presentation.view.model;

import java.math.BigDecimal;
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
	
	private String type;
	
	public static final EntryViewModel fromEntry(Entry entry) {
		EntryViewModel e = new EntryViewModel();
		e.setDueDate(entry.getDueDate().toDate());
		e.setName(entry.getName());
		e.setId(entry.getId());
		e.setValue(entry.getValue());
		
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
}
