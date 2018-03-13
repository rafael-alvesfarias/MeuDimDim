package br.com.mdd.application.repository;

import java.util.List;

import org.joda.time.LocalDate;

import br.com.mdd.domain.model.Entry;

public interface EntryRepository<E extends Entry> extends GenericRepository<E> {
	
	public List<E> findByPeriod(LocalDate dateFrom, LocalDate dateTo, Class<E> clazz);

}
