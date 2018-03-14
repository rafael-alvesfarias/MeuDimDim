package br.com.mdd.application.repository;

import java.util.List;

import org.joda.time.LocalDate;

import br.com.mdd.domain.model.Entry;
import br.com.mdd.domain.model.User;

public interface EntryRepository<E extends Entry> extends GenericRepository<E> {
	
	public List<E> findByPeriodAndUser(LocalDate dateFrom, LocalDate dateTo, User user, Class<E> clazz);
	
	public List<E> findByUser(User user, Class<E> clazz);

}
