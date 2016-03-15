package br.com.mdd.presentation.view.model.income;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import br.com.mdd.domain.model.Budget;
import br.com.mdd.domain.model.Income;

public class AnnualIncomesBudgetViewModel {

	private Set<IncomesSet> incomes;
	private List<BigDecimal> totals;
	private Budget<Income> budget;

	public AnnualIncomesBudgetViewModel(Budget<Income> budget) {
		this(budget, null);
	}

	public AnnualIncomesBudgetViewModel(Budget<Income> budget, Map<Integer, Integer> exclusionsMap) {
		this.budget = budget;
		montar(exclusionsMap);
	}

	private void montar(Map<Integer, Integer> exclusionsMap) {
		this.incomes = new TreeSet<IncomesSet>();
		for (Income income : budget.getEntries()) {
			IncomesSet incomesSet = null;
			for (IncomesSet conj : incomes) {
				if (conj.getName().equals(income.getName())) {
					incomesSet = conj;
				}
			}
			if (incomesSet == null) {
				incomesSet = new IncomesSet();
				incomesSet.setName(income.getName());
				incomesSet.setId(income.getId());
				incomesSet.setIncomes(new TreeMap<Integer, IncomeViewModel>());
				this.incomes.add(incomesSet);
			}
			Integer mes = Integer.valueOf(income.getDueDate().getMonthOfYear());
			//Verifica se não esta na lista de exlusões
			if (exclusionsMap == null || exclusionsMap.get(income.getId()) == null 
					|| mes.compareTo(exclusionsMap.get(income.getId())) < 0) {
				incomesSet.put(mes, IncomeViewModel.fromIncome(income));
			}
		}
		
		totals = new ArrayList<BigDecimal>();
		
		BigDecimal totalGeral = BigDecimal.ZERO;
		for(int i = 1; i <= 12; i++){
			BigDecimal total = BigDecimal.ZERO;
			for (IncomesSet conjunto : incomes) {
				if(conjunto.getIncomes().get(i) != null){
					total = total.add(conjunto.getIncomes().get(i).getValue());
				}
			}
			totals.add(total);
			totalGeral = totalGeral.add(total);
		}
		totals.add(totalGeral);
	}
	
	public Set<IncomesSet> getAnnualIncomes() {
		return incomes;
	}

	public void setAnnualIncomes(Set<IncomesSet> annualIncomes) {
		this.incomes = annualIncomes;
	}
	
	public List<BigDecimal> getTotals() {
		return totals;
	}

	public void setTotais(List<BigDecimal> totals) {
		this.totals = totals;
	}

	public class IncomesSet implements Comparable<IncomesSet> {
		private String name;
		
		private Integer id;

		private Map<Integer, IncomeViewModel> incomes;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Map<Integer, IncomeViewModel> getIncomes() {
			return incomes;
		}

		public void setIncomes(Map<Integer, IncomeViewModel> incomes) {
			this.incomes = incomes;
		}
		
		public void put(Integer key, IncomeViewModel income) {
			this.incomes.put(key, income);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			IncomesSet other = (IncomesSet) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		private AnnualIncomesBudgetViewModel getOuterType() {
			return AnnualIncomesBudgetViewModel.this;
		}

		@Override
		public int compareTo(IncomesSet o) {
			final Collator collator = Collator.getInstance(new Locale("pt", "BR"));
			collator.setStrength(Collator.PRIMARY);
			return collator.compare(this.name, o.getName());
		}
		
		public BigDecimal getTotal(){
			BigDecimal total = BigDecimal.ZERO;
			
			for (Integer mes : incomes.keySet()) {
				total = total.add(incomes.get(mes).getValue());
			}
			
			return total;
		}

	}

}
