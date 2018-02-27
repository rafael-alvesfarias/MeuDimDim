package br.com.mdd.persistence.dao;

import java.util.List;

import org.joda.time.LocalDate;

import br.com.mdd.domain.model.Entry;

public interface EntryDAO<E extends Entry> extends GenericDAO<E> {
	
	public List<E> findByPeriod(LocalDate dateFrom, LocalDate dateTo, Class<E> clazz);

}
