package br.com.mdd.persistence.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mdd.application.repository.CategoryRepository;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.User;
import br.com.mdd.domain.model.Category.CategoryType;

@Repository
public class CategoryHibernateDAO extends GenericHibernateDAO<Category> implements CategoryRepository {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Category> findAllCategoriesByType(CategoryType type) {
		return getSession().createCriteria(Category.class)
				.add(Restrictions.eq("type", type))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}
}
