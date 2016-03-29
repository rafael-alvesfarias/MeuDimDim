package br.com.mdd.persistence.dao;

import java.util.List;

import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Category.CategoryType;

public interface CategoryDAO {
	
	public abstract List<Category> findAllCategoriesByType(CategoryType type);

}
