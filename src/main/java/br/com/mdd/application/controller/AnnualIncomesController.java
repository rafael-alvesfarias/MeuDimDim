package br.com.mdd.application.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Income;
import br.com.mdd.persistence.dao.GenericDAO;
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
	@Qualifier("genericDAO")
	private GenericDAO<Category> categoriesDAO;
	
	@Autowired
	@Qualifier("genericDAO")
	private GenericDAO<Income> incomesDAO;
	
	@RequestMapping("/incomes/annual")
	public String annualIncomes(Model model){
		incomes = getIncomes();
		categories = getCategories();
		this.income = new IncomeViewModel();
		this.income.setCategories(categories);
		model.addAttribute("income", this.income);
		
		generateAnnualBudget(model);
		
		return "/incomes/annualIncomes";
	}

	private void generateAnnualBudget(Model model) {
		Budget<Income> annualIncomesBudget = new Budget<Income>(incomes).annual().withPrediction().generate();
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
	
	@RequestMapping(value = "/income", method = RequestMethod.POST)
	public String salvar(@ModelAttribute(value = "income") IncomeViewModel income, Model model) {
		Income i = new Income(income.getName(), income.getValue(), income.getDueDate());
		i.setId(income.getId());
		
		i.setReceived(income.getReceived());
		i.setCategory(getCategory(income.getCategory()));
		incomesDAO.save(i);
		
		return annualIncomes(model);
	}
	
	@Transactional(readOnly=true)
	@RequestMapping("/updateIncome/{id}")
	public String updateIncome(Model model, @PathVariable Integer id){
		
		Income i = incomesDAO.find(id, Income.class);
		
		generateAnnualBudget(model);
		
		income = IncomeViewModel.fromIncome(i);
		income.setCategories(categories);
		
		model.addAttribute("income", income);
		
		return "/incomes/annualIncomes";
	}
	
	@Transactional
	@RequestMapping(value="/deleteIncome/{id}")
	public String deleteIncome(Model model, @PathVariable Integer id, @RequestParam(required=false) Integer mes) {
		Income income = incomesDAO.find(id, Income.class);
		//Verifica se receita não gerada automaticamente
		if (mes != null && mes > income.getDueDate().getMonthOfYear()) {
			@SuppressWarnings("unchecked")
			Map<Integer, Integer> exclusionsMap = (Map<Integer, Integer>) session.getAttribute("exclusionsMap");
			if (exclusionsMap == null) {
				exclusionsMap = new HashMap<Integer, Integer>();
				session.setAttribute("exclusionsMap", exclusionsMap);
			}
			exclusionsMap.put(id, mes);
		} else {
			incomesDAO.remove(income);
		}
		
		return annualIncomes(model);
	}
	
	private Category getCategory(String nome) {
		for (Category categoria : categories) {
			if (categoria.getName().equals(nome)) {
				return categoria;
			}
		}
		return null;
	}
	
	private Set<Income> getIncomes(){
		Set<Income> incomes = new TreeSet<Income>(incomesDAO.findAll(Income.class));
		
		return incomes;	
	}

	private Set<Category> getCategories() {
		Set<Category> categorias = new TreeSet<>(categoriesDAO.findAll(Category.class));
		
		return categorias;
	}

}
