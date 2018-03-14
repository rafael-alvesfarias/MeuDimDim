package br.com.mdd.application.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mdd.application.repository.EntryRepository;
import br.com.mdd.application.repository.ExpenseRepository;
import br.com.mdd.application.service.UserService;
import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.BudgetBuilder;
import br.com.mdd.domain.model.Entry;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.Income;
import br.com.mdd.domain.model.Investment;
import br.com.mdd.domain.model.User;
import br.com.mdd.presentation.view.model.EntryViewModel;
import br.com.mdd.presentation.view.model.expense.ExpenseViewModel;
import br.com.mdd.presentation.view.model.income.IncomeViewModel;
import br.com.mdd.presentation.view.model.investment.InvestmentViewModel;

@Controller
public class HomeController {
	
	@Autowired
	@Qualifier("entryDAO")
	private EntryRepository<Income> incomesDAO;
	
	@Autowired
	private ExpenseRepository expensesDAO;
	
	@Autowired
	@Qualifier("entryDAO")
	private EntryRepository<Investment> investmentsDAO;
	
	@Autowired
	private UserService userService;
	
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
	
	@Transactional
	@RequestMapping({"/", "/home"})
	public String home(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		
		Budget<Income> incomesBudget = new BudgetBuilder<Income>(getIncomes(user))
				.monthly()
				.build();
		
		List<IncomeViewModel> incomes = incomesBudget
				.getEntries()
					.stream().map(i -> {
						return IncomeViewModel.fromIncome(i);
					}).collect(Collectors.toList());
		
		model.addAttribute("incomes", incomes);
		model.addAttribute("totalIncomes", incomesBudget.getTotal());
		
		Budget<Expense> expensesBudget = new BudgetBuilder<Expense>(getExpenses(user))
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
		
		Budget<Investment> investmentBudget = new BudgetBuilder<Investment>(getInvestments(user))
				.monthly()
				.build();
		
		List<InvestmentViewModel> investments = investmentBudget
				.getEntries()
					.stream().map(i -> {
						return InvestmentViewModel.fromInvestment(i);
					}).collect(Collectors.toList());
		
		model.addAttribute("investments", investments);
		model.addAttribute("totalInvestments", investmentBudget.getTotal());
		
		Set<EntryViewModel> entries = getLastEntries(user)
					.stream().map(e -> {
						return EntryViewModel.fromEntry(e);
					}).collect(Collectors.toSet());
		
		model.addAttribute("lastEntries", entries);
		
		return "/home";
	}
	
	private Set<Income> getIncomes(User user){
		Set<Income> incomes = new TreeSet<Income>(incomesDAO.findByUser(user, Income.class));
		
		return incomes;	
	}
	
	private Set<Expense> getExpenses(User user){
		Set<Expense> expenses = new TreeSet<Expense>(expensesDAO.findByUser(user, Expense.class));
		
		return expenses;	
	}
	
	private Set<Investment> getInvestments(User user){
		Set<Investment> investments = new TreeSet<Investment>(investmentsDAO.findByUser(user, Investment.class));
		
		return investments;	
	}
	
	private List<Entry> getLastEntries(User user) {
		LocalDate today = LocalDate.now();
		List<Entry> entries = new ArrayList<>();
		entries.addAll(expensesDAO.findByPeriodAndUser(today.minusDays(3), today, user, Expense.class));
		entries.addAll(incomesDAO.findByPeriodAndUser(today.minusDays(3), today, user, Income.class));
		entries.addAll(investmentsDAO.findByPeriodAndUser(today.minusDays(3), today, user, Investment.class));
		
		Collections.sort(entries, entryComparator);
		
		return entries;
	}
	
	
}
