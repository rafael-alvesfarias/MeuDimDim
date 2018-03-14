package br.com.mdd.application.repository;

import java.util.List;

import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.domain.model.User;
import br.com.mdd.domain.model.VariableExpense;

public interface ExpenseRepository extends EntryRepository<Expense> {
	
	public abstract List<FixedExpense> findFixedExpensesByUser(User user);
	
	public abstract List<VariableExpense> findVariableExpensesByUser(User user);

}
