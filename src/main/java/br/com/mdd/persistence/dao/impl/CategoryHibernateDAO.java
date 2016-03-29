package br.com.mdd.persistence.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Category.CategoryType;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.domain.model.VariableExpense;
import br.com.mdd.persistence.dao.CategoryDAO;
import br.com.mdd.persistence.dao.ExpenseDAO;

@Repository
public class CategoryHibernateDAO extends GenericHibernateDAO<Category> implements CategoryDAO {

	@Override
	@Transactional(readOnly = true)
	public List<Category> findAllCategoriesByType(CategoryType type) {
		return getSession().createCriteria(Category.class)
				.add(Restrictions.eq("type", type))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}
}
