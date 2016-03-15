package br.com.mdd.persistence.dao;

import java.util.List;

import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;

public interface ExpenseDAO extends GenericDAO<Expense> {
	
	public abstract List<FixedExpense> findAllFixedExpenses();
	
	public abstract List<Expense> findAllVariableExpenses();

}
