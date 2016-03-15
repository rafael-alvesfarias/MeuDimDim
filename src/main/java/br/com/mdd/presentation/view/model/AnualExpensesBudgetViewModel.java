package br.com.mdd.presentation.view.model;

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
import br.com.mdd.domain.model.Expense;

public class AnualExpensesBudgetViewModel<E extends Expense> {

	private Set<ConjuntoDespesas> despesas;
	private List<BigDecimal> totais;
	private Budget<E> orcamento;

	public AnualExpensesBudgetViewModel(Budget<E> orcamento) {
		this(orcamento, null);
	}

	public AnualExpensesBudgetViewModel(Budget<E> orcamento, Map<Integer, Integer> exclusionsMap) {
		this.orcamento = orcamento;
		montar(exclusionsMap);
	}

	private void montar(Map<Integer, Integer> exclusionsMap) {
		despesas = new TreeSet<ConjuntoDespesas>();
		for (Expense itemOrcamento : orcamento.getEntries()) {
			ConjuntoDespesas conjuntoDespesas = null;
			for (ConjuntoDespesas conj : despesas) {
				if (conj.getNomeDespesa().equals(itemOrcamento.getName())) {
					conjuntoDespesas = conj;
				}
			}
			if (conjuntoDespesas == null) {
				conjuntoDespesas = new ConjuntoDespesas();
				conjuntoDespesas.setNomeDespesa(itemOrcamento.getName());
				conjuntoDespesas.setIdDespesa(itemOrcamento.getId());
				
				conjuntoDespesas.setDespesas(new TreeMap<Integer, Expense>());
				despesas.add(conjuntoDespesas);
			}
			Integer mes = Integer.valueOf(itemOrcamento.getDueDate().getMonthOfYear());
			//Verifica se não esta na lista de exlusões
			if (exclusionsMap == null || exclusionsMap.get(itemOrcamento.getId()) == null 
					|| mes.compareTo(exclusionsMap.get(itemOrcamento.getId())) < 0) {
				conjuntoDespesas.put(mes, itemOrcamento);
			}
		}
		
		totais = new ArrayList<BigDecimal>();
		
		BigDecimal totalGeral = BigDecimal.ZERO;
		for(int i = 1; i <= 12; i++){
			BigDecimal total = BigDecimal.ZERO;
			for (ConjuntoDespesas conjunto : despesas) {
				if(conjunto.getDespesas().get(i) != null){
					total = total.add(conjunto.getDespesas().get(i).getValue());
				}
			}
			totais.add(total);
			totalGeral = totalGeral.add(total);
		}
		totais.add(totalGeral);
	}
	
	public Set<ConjuntoDespesas> getDespesasAnuais() {
		return despesas;
	}

	public void setDespesasAnuais(Set<ConjuntoDespesas> despesasAnuais) {
		this.despesas = despesasAnuais;
	}
	
	public List<BigDecimal> getTotais() {
		return totais;
	}

	public void setTotais(List<BigDecimal> totais) {
		this.totais = totais;
	}

	public class ConjuntoDespesas implements Comparable<ConjuntoDespesas> {
		private String nomeDespesa;
		
		private Integer idDespesa;

		private Map<Integer, Expense> despesas;

		public String getNomeDespesa() {
			return nomeDespesa;
		}

		public void setNomeDespesa(String nomeDespesa) {
			this.nomeDespesa = nomeDespesa;
		}

		public Integer getIdDespesa() {
			return idDespesa;
		}

		public void setIdDespesa(Integer idDespesa) {
			this.idDespesa = idDespesa;
		}

		public Map<Integer, Expense> getDespesas() {
			return despesas;
		}

		public void setDespesas(Map<Integer, Expense> despesas) {
			this.despesas = despesas;
		}
		
		
		public void put(Integer key, Expense despesa) {
			this.despesas.put(key, despesa);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((nomeDespesa == null) ? 0 : nomeDespesa.hashCode());
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
			ConjuntoDespesas other = (ConjuntoDespesas) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (nomeDespesa == null) {
				if (other.nomeDespesa != null)
					return false;
			} else if (!nomeDespesa.equals(other.nomeDespesa))
				return false;
			return true;
		}

		private AnualExpensesBudgetViewModel<E> getOuterType() {
			return AnualExpensesBudgetViewModel.this;
		}

		@Override
		public int compareTo(ConjuntoDespesas o) {
			final Collator collator = Collator.getInstance(new Locale("pt", "BR"));
			collator.setStrength(Collator.PRIMARY);
			return collator.compare(this.nomeDespesa, o.getNomeDespesa());
		}
		
		public BigDecimal getTotal(){
			BigDecimal total = BigDecimal.ZERO;
			
			for (Integer mes : despesas.keySet()) {
				total = total.add(despesas.get(mes).getValue());
			}
			
			return total;
		}

	}

}
