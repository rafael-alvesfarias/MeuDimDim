package br.com.mdd.application.controller;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Category.CategoryType;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.domain.model.VariableExpense;
import br.com.mdd.persistence.dao.CategoryDAO;
import br.com.mdd.persistence.dao.ExpenseDAO;
import br.com.mdd.presentation.view.model.expense.ExpenseViewModel;

@Controller
public class ExpensesController {
	
	private Set<Category> categorias;
	
	private ExpenseViewModel expense;
	
	@Autowired
	private ExpenseDAO expensesDAO;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@RequestMapping("/expense")
	public String expense(Model model){
		categorias = getCategorias();
		expense = new ExpenseViewModel();
		expense.setCategories(categorias);
		model.addAttribute("expense", expense);
		
		return "/expenses/expense";
	}

	@RequestMapping(value = "/expense", method = RequestMethod.POST)
	public String salvar(@ModelAttribute(value = "expense") @Valid ExpenseViewModel expense
			,BindingResult bindingResult , Model model) {
		if (bindingResult.hasErrors()) {
			return "/expenses/expense";
		}
		Expense d = null;
		if(expense.isFixedExpense()) {
			d = new FixedExpense(expense.getName(), expense.getValue(), new LocalDate(expense.getDueDate()));
		} else {
			d = new VariableExpense(expense.getName(), expense.getValue(), new LocalDate(expense.getDueDate()));
		}
		d.setId(expense.getId());
		
		d.setPaid(expense.isPaid());
		d.setCategory(getCategoria(expense.getCategory()));
		expensesDAO.save(d);
		
		return expense(model);
	}
	
	private Category getCategoria(String nome) {
		for (Category categoria : categorias) {
			if (categoria.getName().equals(nome)) {
				return categoria;
			}
		}
		return null;
	}
	
	private Set<Category> getCategorias() {
		Set<Category> categorias = new TreeSet<>(categoryDAO.findAllCategoriesByType(CategoryType.EXPENSE));
		
		return categorias;
	}

}
