package br.com.mdd.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mdd.application.repository.ExpenseRepository;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.domain.model.User;
import br.com.mdd.domain.model.VariableExpense;

@Repository
public class ExpenseHibernateDAO extends EntryHibernateDAO<Expense> implements ExpenseRepository {
	
	@Override
	@Transactional(readOnly = true)
	public List<FixedExpense> findFixedExpensesByUser(User user) {
		String query = "select e FROM FixedExpense e WHERE e.user = :user";
			return getSession()
					.createQuery(query, FixedExpense.class)
					.setParameter("user", user)
					.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VariableExpense> findVariableExpensesByUser(User user) {
		String query = "select e FROM VariableExpense e WHERE e.user = :user";
		return getSession()
				.createQuery(query, VariableExpense.class)
				.setParameter("user", user)
				.getResultList();
	}
}
