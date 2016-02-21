package br.com.mdd.persistence.dao;

import java.util.List;

import br.com.mdd.domain.model.Expense;

public interface ExpenseDAO extends GenericDAO<Expense> {
	
	public abstract List<Expense> findAllFixedExpenses();
	
	public abstract List<Expense> findAllVariableExpenses();

}
