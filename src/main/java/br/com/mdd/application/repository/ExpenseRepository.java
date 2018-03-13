package br.com.mdd.application.repository;

import java.util.List;

import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;

public interface ExpenseRepository extends EntryRepository<Expense> {
	
	public abstract List<FixedExpense> findAllFixedExpenses();
	
	public abstract List<Expense> findAllVariableExpenses();

}
