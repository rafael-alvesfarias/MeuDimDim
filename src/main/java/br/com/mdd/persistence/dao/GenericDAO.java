package br.com.mdd.persistence.dao;

import java.io.Serializable;
import java.util.List;

import org.joda.time.LocalDate;

import br.com.mdd.domain.model.Expense;

public interface GenericDAO<T> {
	
	public void save(T t);

	public void remove(T t);
	
	public T find(Serializable id, Class<T> clazz);
	
	public List<T> findAll(Class<T> clazz);
}
