package br.com.mdd.persistence.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.persistence.dao.ExpenseDAO;

@Repository
public class ExpenseHibernateDAO extends GenericHibernateDAO<Expense> implements ExpenseDAO {
	
	@Override
	@Transactional(readOnly = true)
	public List<FixedExpense> findAllFixedExpenses() {
		return getSession().createCriteria(FixedExpense.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Expense> findAllVariableExpenses() {
		return getSession().createCriteria(Expense.class)
				.add(Restrictions.eq("class", "Expense"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

}
