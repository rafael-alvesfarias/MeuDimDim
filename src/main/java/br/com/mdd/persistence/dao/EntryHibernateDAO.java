package br.com.mdd.persistence.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mdd.application.repository.EntryRepository;
import br.com.mdd.domain.model.Entry;
import br.com.mdd.domain.model.User;

@Repository("entryDAO")
public class EntryHibernateDAO<E extends Entry> extends GenericHibernateDAO<E> implements EntryRepository<E> {
	
	@Override
	@Transactional(readOnly = true)
	public List<E> findByPeriodAndUser(LocalDate dateFrom, LocalDate dateTo, User user, Class<E> clazz) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(clazz);
		Root<E> e = query.from(clazz);
		ParameterExpression<LocalDate> dateFromParam = builder.parameter(LocalDate.class, "dateFrom");
		ParameterExpression<LocalDate> dateToParam = builder.parameter(LocalDate.class, "dateTo");
		ParameterExpression<User> userParam = builder.parameter(User.class, "user");
		query.where(
				builder.equal(e.get("user"), userParam),
				builder.between(e.get("dueDate"), dateFromParam, dateToParam));

		return getSession().createQuery(query)
				.setParameter("user", user)
				.setParameter("dateFrom", dateFrom)
				.setParameter("dateTo", dateTo)
				.getResultList();
	}

	@Override
	public List<E> findByUser(User user, Class<E> clazz) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(clazz);
		Root<E> e = query.from(clazz);
		ParameterExpression<User> userParam = builder.parameter(User.class, "user");
		query.where(builder.equal(e.get("user"), userParam));

		return getSession().createQuery(query).setParameter("user", user).getResultList();
	}
}
