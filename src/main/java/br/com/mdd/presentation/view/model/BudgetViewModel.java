package br.com.mdd.presentation.view.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.Entry;

public class BudgetViewModel {
	
	private String name;
	
	private BigDecimal total;
	
	private List<EntryViewModel> entries;
	
	public static BudgetViewModel fromBudget(Budget<Entry> budget) {
		BudgetViewModel b = new BudgetViewModel();
		if (budget != null) {
			b.setName(budget.getName());
			b.setTotal(budget.getTotal());
			List<EntryViewModel> entries = new ArrayList<>();
			if (budget.getEntries() != null) {
				entries = budget.getEntries().stream()
						.map(EntryViewModel::fromEntry)
						.collect(Collectors.toList());
			}
			b.setEntries(entries);
		}
		return b;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EntryViewModel> getEntries() {
		return entries;
	}

	public void setEntries(List<EntryViewModel> entries) {
		this.entries = entries;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
