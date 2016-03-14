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
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.domain.model.Income;
import br.com.mdd.persistence.dao.ExpenseDAO;
import br.com.mdd.persistence.dao.GenericDAO;
import br.com.mdd.presentation.view.model.AnualFixedExpensesBudgetViewModel;
import br.com.mdd.presentation.view.model.ExpenseViewModel;
import br.com.mdd.presentation.view.model.IncomeViewModel;

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
	
	@RequestMapping("/incomes/annual")
	public String annualIncomes(Model model){
		incomes = getDespesasFixas();
		categories = getCategorias();
		income = new IncomeViewModel();
		income.setCategories(categories);
		model.addAttribute("despesa", despesa);
		
		gerarOrcamentoAnual(model);
		
		return "/despesas/despesasAnuais";
	}

	private void gerarOrcamentoAnual(Model model) {
		Budget annualIncomesBudget = new Budget(incomes).annual().withPrediction().generate();
		AnualFixedExpensesBudgetViewModel orcamentoViewModelDespesasFixasViewModel;
		AnualFixedExpensesBudgetViewModel orcamentoViewModelDespesasVariaveisViewModel = new AnualFixedExpensesBudgetViewModel(orcamentoDespesasVariaveis);
		@SuppressWarnings("unchecked")
		Map<Integer, Integer> exclusionsMap = (Map<Integer, Integer>) session.getAttribute("exclusionsMap");
		if (exclusionsMap != null) {
			orcamentoViewModelDespesasFixasViewModel = new AnualFixedExpensesBudgetViewModel(annualIncomesBudget, exclusionsMap);
		} else {
			orcamentoViewModelDespesasFixasViewModel = new AnualFixedExpensesBudgetViewModel(annualIncomesBudget);
		}
		model.addAttribute("orcamentoDespesasFixas", orcamentoViewModelDespesasFixasViewModel);
		model.addAttribute("orcamentoDespesasVariaveis", orcamentoViewModelDespesasVariaveisViewModel);
	}
	
	@RequestMapping(value = "/despesa", method = RequestMethod.POST)
	public String salvar(@ModelAttribute(value = "despesa") ExpenseViewModel despesa, Model model) {
		Expense d = null;
		if(despesa.getDespesaFixa()) {
			d = new FixedExpense(despesa.getDescricao(), despesa.getValor(), despesa.getDataLancamento());
		} else {
			d = new Expense(despesa.getDescricao(), despesa.getValor(), despesa.getDataLancamento());
		}
		d.setId(despesa.getId());
		
		d.setPaid(despesa.getPago());
		d.setCategory(getCategoria(despesa.getCategoria()));
		expensesDAO.save(d);
		
		return annualIncomes(model);
	}
	
	@Transactional(readOnly=true)
	@RequestMapping("/editarDespesa/{id}")
	public String editarDespesa(Model model, @PathVariable Integer id){
		
		Expense expense = expensesDAO.find(id, Expense.class);
		
		gerarOrcamentoAnual(model);
		
		despesa = ExpenseViewModel.fromExpense(expense);
		despesa.setCategorias(categories);
		
		model.addAttribute("despesa", despesa);
		
		return "/despesas/despesasAnuais";
	}
	
	@Transactional
	@RequestMapping("/excluirDespesa/{id}")
	public String excluirDespesa(Model model, @PathVariable Integer id, @RequestParam(required=false) Integer mes) {
		Expense expense = expensesDAO.find(id, Expense.class);
		//Verifica se despesa não gerada automáticamente
		if (mes != null && mes > expense.getMaturityDate().getMonthOfYear()) {
			@SuppressWarnings("unchecked")
			Map<Integer, Integer> exclusionsMap = (Map<Integer, Integer>) session.getAttribute("exclusionsMap");
			if (exclusionsMap == null) {
				exclusionsMap = new HashMap<Integer, Integer>();
				session.setAttribute("exclusionsMap", exclusionsMap);
			}
			exclusionsMap.put(id, mes);
		} else {
			expensesDAO.remove(expense);
		}
		
		return annualIncomes(model);
	}
	
	private Category getCategoria(String nome) {
		for (Category categoria : categories) {
			if (categoria.getName().equals(nome)) {
				return categoria;
			}
		}
		return null;
	}
	
	private Set<Expense> getDespesasFixas(){
		Set<Expense> despesas = new TreeSet<Expense>(expensesDAO.findAllFixedExpenses());
		
		return despesas;	
	}
	
	private Set<Expense> getDespesasVariaveis(){
		Set<Expense> despesas = new TreeSet<Expense>(expensesDAO.findAllVariableExpenses());
		
		return despesas;	
	}
	
	private Set<Category> getCategorias() {
		Set<Category> categorias = new TreeSet<>(categoriesDAO.findAll(Category.class));
		
		return categorias;
	}

}
