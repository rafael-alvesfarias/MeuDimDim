package br.com.mdd.presentation.model;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import br.com.mdd.domain.model.Despesa;
import br.com.mdd.domain.model.DespesaFixa;
import br.com.mdd.domain.model.Orcamento;

public class OrcamentoDespesasAnuaisViewModel {

	private Set<ConjuntoDespesas> despesas;
	private List<BigDecimal> totais;
	private Orcamento orcamento;

	public OrcamentoDespesasAnuaisViewModel(Orcamento orcamento) {
		this.orcamento = orcamento;
		montar();
	}

	private void montar() {
		despesas = new TreeSet<ConjuntoDespesas>();
		for (Despesa itemOrcamento : orcamento.getDespesas()) {
			ConjuntoDespesas conjuntoDespesas = null;
			for (ConjuntoDespesas conj : despesas) {
				if (conj.getNomeDespesa().equals(itemOrcamento.getDescricao())) {
					conjuntoDespesas = conj;
				}
			}
			if (conjuntoDespesas == null) {
				conjuntoDespesas = new ConjuntoDespesas();
				conjuntoDespesas.setNomeDespesa(itemOrcamento.getDescricao());
				
				conjuntoDespesas.setDespesas(new TreeMap<Integer, Despesa>());
				despesas.add(conjuntoDespesas);
			}
			Integer mes = Integer.valueOf(itemOrcamento.getDataLancamento().getMonthOfYear());
			conjuntoDespesas.put(mes, itemOrcamento);
		}
		
		totais = new ArrayList<BigDecimal>();
		
		BigDecimal totalGeral = BigDecimal.ZERO;
		for(int i = 1; i <= 12; i++){
			BigDecimal total = BigDecimal.ZERO;
			for (ConjuntoDespesas conjunto : despesas) {
				if(conjunto.getDespesas().get(i) != null){
					total = total.add(conjunto.getDespesas().get(i).getValor());
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

		private Map<Integer, Despesa> despesas;

		public String getNomeDespesa() {
			return nomeDespesa;
		}

		public void setNomeDespesa(String nomeDespesa) {
			this.nomeDespesa = nomeDespesa;
		}

		public Map<Integer, Despesa> getDespesas() {
			return despesas;
		}

		public void setDespesas(Map<Integer, Despesa> despesas) {
			this.despesas = despesas;
		}
		
		
		public void put(Integer key, Despesa despesa) {
			if (despesa instanceof DespesaFixa) {
				this.despesas.put(key, (DespesaFixa) despesa);
			}
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

		private OrcamentoDespesasAnuaisViewModel getOuterType() {
			return OrcamentoDespesasAnuaisViewModel.this;
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
				total = total.add(despesas.get(mes).getValor());
			}
			
			return total;
		}

	}

}
