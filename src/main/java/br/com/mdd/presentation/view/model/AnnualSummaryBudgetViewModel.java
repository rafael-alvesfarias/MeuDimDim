package br.com.mdd.presentation.view.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.Income;
import br.com.mdd.presentation.view.model.income.IncomeGroup;
import br.com.mdd.presentation.view.model.income.IncomeViewModel;

public class AnnualSummaryBudgetViewModel {

	private Set<IncomeGroup> incomeGroups;
	private List<BigDecimal> totalIncomes;
	private Budget<Income> budget;

	public AnnualSummaryBudgetViewModel(Budget<Income> budget) {
		this(budget, null);
	}

	public AnnualSummaryBudgetViewModel(Budget<Income> budget, Map<Integer, Integer> exclusionsMap) {
		this.budget = budget;
		montar(exclusionsMap);
	}

	private void montar(Map<Integer, Integer> exclusionsMap) {
		this.incomeGroups = new TreeSet<IncomeGroup>();
		for (Income income : budget.getEntries()) {
			IncomeGroup incomeGroup = null;
			for (IncomeGroup group : incomeGroups) {
				if (group.getName().equals(income.getName())) {
					incomeGroup = group;
				}
			}
			if (incomeGroup == null) {
				incomeGroup = new IncomeGroup();
				incomeGroup.setName(income.getName());
				incomeGroup.setId(income.getId());
				this.incomeGroups.add(incomeGroup);
			}
			Integer mes = Integer.valueOf(income.getDueDate().getMonthOfYear());
			//Verifica se não esta na lista de exlusões
			if (exclusionsMap == null || exclusionsMap.get(income.getId()) == null 
					|| mes.compareTo(exclusionsMap.get(income.getId())) < 0) {
				incomeGroup.put(mes, IncomeViewModel.fromIncome(income));
			}
		}
		
		totalIncomes = new ArrayList<BigDecimal>();
		
		BigDecimal totalGeral = BigDecimal.ZERO;
		for (int i = 1; i <= 12; i++) {
			BigDecimal total = BigDecimal.ZERO;
			for (IncomeGroup conjunto : incomeGroups) {
				if(conjunto.getIncomes().get(i) != null) {
					total = total.add(conjunto.getIncomes().get(i).getValue());
				}
			}
			totalIncomes.add(total);
			totalGeral = totalGeral.add(total);
		}
		totalIncomes.add(totalGeral);
	}
	
	public Set<IncomeGroup> getAnnualIncomes() {
		return incomeGroups;
	}

	public void setAnnualIncomes(Set<IncomeGroup> annualIncomes) {
		this.incomeGroups = annualIncomes;
	}
	
	public List<BigDecimal> getTotalIncomes() {
		return totalIncomes;
	}
}
