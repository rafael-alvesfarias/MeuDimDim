package br.com.mdd.application.controller;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mdd.application.repository.CategoryRepository;
import br.com.mdd.application.repository.GenericRepository;
import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.BudgetBuilder;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Category.CategoryType;
import br.com.mdd.domain.model.Income;
import br.com.mdd.presentation.view.model.income.AnnualIncomesBudgetViewModel;
import br.com.mdd.presentation.view.model.income.IncomeViewModel;

@Controller
public class AnnualIncomesController {
	
	private Set<Income> incomes;
	
	private Set<Category> categories;
	
	private IncomeViewModel income;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CategoryRepository categoryDAO;
	
	@Autowired
	@Qualifier("genericDAO")
	private GenericRepository<Income> incomesDAO;
	
	@RequestMapping("/incomes/annual")
	public String annualIncomes(Model model){
		incomes = getIncomes();
		categories = getCategories();
		this.income = new IncomeViewModel();
		this.income.setCategories(categories);
		model.addAttribute("income", this.income);
		
		generateAnnualBudget(model);
		
		return "/budgets/annualIncomes";
	}

	private void generateAnnualBudget(Model model) {
		Budget<Income> annualIncomesBudget = new BudgetBuilder<>(incomes).annual().build();
		AnnualIncomesBudgetViewModel incomesBudgetViewModel;
		@SuppressWarnings("unchecked")
		Map<Integer, Integer> exclusionsMap = (Map<Integer, Integer>) session.getAttribute("exclusionsMap");
		if (exclusionsMap != null) {
			incomesBudgetViewModel = new AnnualIncomesBudgetViewModel(annualIncomesBudget, exclusionsMap);
		} else {
			incomesBudgetViewModel = new AnnualIncomesBudgetViewModel(annualIncomesBudget);
		}
		model.addAttribute("incomesBudget", incomesBudgetViewModel);
	}
	
	private Set<Income> getIncomes(){
		Set<Income> incomes = new TreeSet<Income>(incomesDAO.findAll(Income.class));
		
		return incomes;	
	}

	private Set<Category> getCategories() {
		Set<Category> categorias = new TreeSet<>(categoryDAO.findAllCategoriesByType(CategoryType.INCOME));
		
		return categorias;
	}

}
