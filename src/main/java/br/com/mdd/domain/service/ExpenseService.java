package br.com.mdd.domain.service;

import java.util.List;

import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;

public interface ExpenseService {
	
	public void create(Expense expense);
	
	public void update(Expense expense);
	
	public void delete(Expense expense);
	
	public Expense getById(Integer id);
	
	public List<FixedExpense> getAllFixedExpenses();
	
	public List<Expense> getAllExpenses();

}
