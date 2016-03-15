package br.com.mdd.presentation.view.model.income;

import java.math.BigDecimal;
import java.util.Set;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Income;

public class IncomeViewModel {
	
	private Integer id;
	
	private String name;
	
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal value;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dueDate;
	
	private String category = new String();
	
	private Boolean received;
	
	private Set<Category> categories;
	
	public static final IncomeViewModel fromIncome(Income income) {
		IncomeViewModel i = new IncomeViewModel();
		i.setCategory(income.getCategory().getName());
		i.setDueDate(income.getDueDate());
		i.setName(income.getName());
		i.setId(income.getId());
		i.setReceived(income.getReceived());
		i.setValue(income.getValue());
		
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

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Boolean getReceived() {
		return received;
	}

	public void setReceived(Boolean received) {
		this.received = received;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
