package br.com.mdd.presentation.view.model.income;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class IncomeGroup implements Comparable<IncomeGroup> {
	private String name;
	
	private Integer id;

	private Map<Integer, IncomeViewModel> incomes;
	
	public IncomeGroup() {
		this.incomes = new TreeMap<Integer, IncomeViewModel>();
	}

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

	public void put(Integer key, IncomeViewModel income) {
		this.incomes.put(key, income);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((incomes == null) ? 0 : incomes.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		IncomeGroup other = (IncomeGroup) obj;
		if (incomes == null) {
			if (other.incomes != null)
				return false;
		} else if (!incomes.equals(other.incomes))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(IncomeGroup o) {
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