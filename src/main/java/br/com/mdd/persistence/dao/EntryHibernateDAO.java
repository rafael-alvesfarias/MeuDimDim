package br.com.mdd.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mdd.application.repository.EntryRepository;
import br.com.mdd.domain.model.Entry;

@Repository("entryDAO")
public class EntryHibernateDAO<E extends Entry> extends GenericHibernateDAO<E> implements EntryRepository<E> {
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<E> findByPeriod(LocalDate dateFrom, LocalDate dateTo, Class<E> clazz) {
		return getSession().createCriteria(clazz)
				.add(Restrictions.between("dueDate", dateFrom, dateTo))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}
}
