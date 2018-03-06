package br.com.mdd.application.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.BudgetBuilder;
import br.com.mdd.domain.model.Entry;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.Income;
import br.com.mdd.domain.model.Investment;
import br.com.mdd.persistence.dao.EntryDAO;
import br.com.mdd.persistence.dao.ExpenseDAO;
import br.com.mdd.presentation.view.model.EntryViewModel;
import br.com.mdd.presentation.view.model.expense.ExpenseViewModel;
import br.com.mdd.presentation.view.model.income.IncomeViewModel;
import br.com.mdd.presentation.view.model.investment.InvestmentViewModel;

@Controller
public class HomeController {
	
	@Autowired
	@Qualifier("entryDAO")
	private EntryDAO<Income> incomesDAO;
	
	@Autowired
	private ExpenseDAO expensesDAO;
	
	@Autowired
	@Qualifier("entryDAO")
	private EntryDAO<Investment> investmentsDAO;
	
	private Comparator<Entry> entryComparator;
	
	public HomeController() {
		this.entryComparator = new Comparator<Entry>() {
			
			@Override
			public int compare(Entry o1, Entry o2) {
				if (o1 == null || o2 == null) {
					throw new IllegalArgumentException("invalid arguments");
				}
				if (o1.getDueDate() != null) {
					return o1.getDueDate().compareTo(o2.getDueDate());
				} else if (o2.getDueDate() != null) {
					return -1;
				} else {
					return 0;
				}
			}
		};
	}
	
	@RequestMapping("/")
	public String index(Model model){
		return home(model);
	}
	
	@RequestMapping("/home")
	public String home(Model model){
		Budget<Income> incomesBudget = new BudgetBuilder<Income>(getIncomes())
				.monthly()
				.build();
		
		List<IncomeViewModel> incomes = incomesBudget
				.getEntries()
					.stream().map(i -> {
						return IncomeViewModel.fromIncome(i);
					}).collect(Collectors.toList());
		
		model.addAttribute("incomes", incomes);
		model.addAttribute("totalIncomes", incomesBudget.getTotal());
		
		Budget<Expense> expensesBudget = new BudgetBuilder<Expense>(getExpenses())
				.monthly()
				.withPrediction()
				.build();
		
		List<ExpenseViewModel> expenses = expensesBudget
				.getEntries()
					.stream().map(e -> {
						return ExpenseViewModel.fromExpense(e);
					}).collect(Collectors.toList());
		
		model.addAttribute("expenses", expenses);
		model.addAttribute("totalExpenses", expensesBudget.getTotal());
		
		Budget<Investment> investmentBudget = new BudgetBuilder<Investment>(getInvestments())
				.monthly()
				.build();
		
		List<InvestmentViewModel> investments = investmentBudget
				.getEntries()
					.stream().map(i -> {
						return InvestmentViewModel.fromInvestment(i);
					}).collect(Collectors.toList());
		
		model.addAttribute("investments", investments);
		model.addAttribute("totalInvestments", investmentBudget.getTotal());
		
		Set<EntryViewModel> entries = getLastEntries()
					.stream().map(e -> {
						return EntryViewModel.fromEntry(e);
					}).collect(Collectors.toSet());
		
		model.addAttribute("lastEntries", entries);
		
		return "/home";
	}
	
	private Set<Income> getIncomes(){
		Set<Income> incomes = new TreeSet<Income>(incomesDAO.findAll(Income.class));
		
		return incomes;	
	}
	
	private Set<Expense> getExpenses(){
		Set<Expense> expenses = new TreeSet<Expense>(expensesDAO.findAll(Expense.class));
		
		return expenses;	
	}
	
	private Set<Investment> getInvestments(){
		Set<Investment> investments = new TreeSet<Investment>(investmentsDAO.findAll(Investment.class));
		
		return investments;	
	}
	
	private List<Entry> getLastEntries() {
		LocalDate today = LocalDate.now();
		List<Entry> entries = new ArrayList<>();
		entries.addAll(expensesDAO.findByPeriod(today.minusDays(3), today, Expense.class));
		entries.addAll(incomesDAO.findByPeriod(today.minusDays(3), today, Income.class));
		entries.addAll(investmentsDAO.findByPeriod(today.minusDays(3), today, Investment.class));
		
		Collections.sort(entries, entryComparator);
		
		return entries;
	}
	
	
}
