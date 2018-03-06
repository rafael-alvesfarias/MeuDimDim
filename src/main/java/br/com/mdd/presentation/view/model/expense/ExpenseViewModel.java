package br.com.mdd.presentation.view.model.expense;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;

public class ExpenseViewModel {
	
	private Integer id;
	
	private String name;
	
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal value;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dueDate;
	
	private String category;
	
	private boolean fixedExpense;
	
	private boolean paid;
	
	private Set<Category> categories;
	
	private String error;
	
	public static final ExpenseViewModel fromExpense(Expense expense) {
		ExpenseViewModel e = new ExpenseViewModel();
		if (expense.getCategory() != null) {
			e.setCategory(expense.getCategory().getName());
		}
		e.setDueDate(expense.getDueDate().toDate());
		e.setName(expense.getName());
		e.setId(expense.getId());
		
		e.setValue(expense.getValue());
		
		if (Boolean.TRUE.equals(expense.getPaid())) {
			e.setPaid(true);
		} else {
			e.setPaid(false);
		}
		
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
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

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
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

}
