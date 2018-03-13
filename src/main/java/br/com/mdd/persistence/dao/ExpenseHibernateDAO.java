package br.com.mdd.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mdd.application.repository.ExpenseRepository;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.domain.model.VariableExpense;

@Repository
public class ExpenseHibernateDAO extends EntryHibernateDAO<Expense> implements ExpenseRepository {
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<FixedExpense> findAllFixedExpenses() {
		return getSession().createCriteria(FixedExpense.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Expense> findAllVariableExpenses() {
		return getSession().createCriteria(VariableExpense.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}
}
