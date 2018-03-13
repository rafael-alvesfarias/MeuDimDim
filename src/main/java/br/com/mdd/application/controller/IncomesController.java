package br.com.mdd.application.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
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

import br.com.mdd.application.repository.CategoryRepository;
import br.com.mdd.application.repository.GenericRepository;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Category.CategoryType;
import br.com.mdd.domain.model.Income;
import br.com.mdd.presentation.view.model.income.IncomeViewModel;

@Controller
public class IncomesController {
	
	private Set<Category> categories;
	
	private IncomeViewModel income;
	
	@Autowired
	@Qualifier("genericDAO")
	private GenericRepository<Income> incomesDAO;
	
	@Autowired
	private CategoryRepository categoryDAO;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping(value = "/newIncome", method = RequestMethod.GET)
	public String newIncome(Model model){
		categories = getCategories();
		income = new IncomeViewModel();
		income.setCategories(categories);
		model.addAttribute("income", income);
		
		return "/incomes/income";
	}

	@RequestMapping(value = "/income", method = RequestMethod.POST)
	public String saveIncome(@ModelAttribute(value = "income") IncomeViewModel income, Model model) {
		Income i = new Income(income.getName(), income.getValue(), new LocalDate(income.getDueDate()));
		i.setId(income.getId());
		
		i.setReceived(income.getReceived());
		i.setCategory(getCategory(income.getCategory()));
		incomesDAO.save(i);
		
		return newIncome(model);
	}
	
	@Transactional(readOnly=true)
	@RequestMapping("/updateIncome/{id}")
	public String updateIncome(Model model, @PathVariable Integer id){
		
		Income i = incomesDAO.find(id, Income.class);
		
		//generateAnnualBudget(model);
		
		income = IncomeViewModel.fromIncome(i);
		income.setCategories(categories);
		
		model.addAttribute("income", income);
		
		return "/incomes/income";
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
		
		return "/home";
	}
	
	private Category getCategory(String nome) {
		for (Category categoria : categories) {
			if (categoria.getName().equals(nome)) {
				return categoria;
			}
		}
		return null;
	}
	
	private Set<Category> getCategories() {
		Set<Category> categorias = new TreeSet<>(categoryDAO.findAllCategoriesByType(CategoryType.INCOME));
		
		return categorias;
	}

}
