package br.com.mdd.application.repository;

import java.util.List;

import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Category.CategoryType;

public interface CategoryRepository {
	
	public abstract List<Category> findAllCategoriesByType(CategoryType type);

}
