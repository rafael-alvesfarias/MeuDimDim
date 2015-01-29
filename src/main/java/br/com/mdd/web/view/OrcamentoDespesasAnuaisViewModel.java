package br.com.mdd.web.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.mdd.domain.DespesaFixa;
import br.com.mdd.domain.Orcamento;

public class OrcamentoDespesasAnuaisViewModel {

	private List<ConjuntoDespesas> despesasAnuais;
	private List<BigDecimal> totais;
	private Orcamento orcamento;

	public OrcamentoDespesasAnuaisViewModel(Orcamento orcamento) {
		this.orcamento = orcamento;
		montar();
	}

	private void montar() {
		despesasAnuais = new ArrayList<ConjuntoDespesas>();
		for (DespesaFixa itemOrcamento : orcamento.getDespesasFixas()) {
			ConjuntoDespesas conjuntoDespesas = null;
			for (ConjuntoDespesas conj : despesasAnuais) {
				if (conj.getNomeDespesa().equals(itemOrcamento.getDescricao())) {
					conjuntoDespesas = conj;
				}
			}
			if (conjuntoDespesas == null) {
				conjuntoDespesas = new ConjuntoDespesas();
				conjuntoDespesas.setNomeDespesa(itemOrcamento.getDescricao());
				
				conjuntoDespesas.setDespesasFixas(new TreeMap<Integer, DespesaFixa>());
				despesasAnuais.add(conjuntoDespesas);
			}
			Integer mes = Integer.valueOf(itemOrcamento.getDataLancamento().getMonthOfYear());
			conjuntoDespesas.getDespesasFixas().put(mes, itemOrcamento);
		}
		
		totais = new ArrayList<BigDecimal>();
		
		BigDecimal totalGeral = BigDecimal.ZERO;
		for(int i = 1; i <= 12; i++){
			BigDecimal total = BigDecimal.ZERO;
			for (ConjuntoDespesas conjunto : despesasAnuais) {
				if(conjunto.getDespesasFixas().get(i) != null){
					total = total.add(conjunto.getDespesasFixas().get(i).getValor());
				}
			}
			totais.add(total);
			totalGeral = totalGeral.add(total);
		}
		totais.add(totalGeral);
	}
	
	public List<ConjuntoDespesas> getDespesasAnuais() {
		return despesasAnuais;
	}

	public void setDespesasAnuais(List<ConjuntoDespesas> despesasAnuais) {
		this.despesasAnuais = despesasAnuais;
	}
	
	public List<BigDecimal> getTotais() {
		return totais;
	}

	public void setTotais(List<BigDecimal> totais) {
		this.totais = totais;
	}

	public class ConjuntoDespesas implements Comparable<ConjuntoDespesas> {
		private String nomeDespesa;

		private Map<Integer, DespesaFixa> despesasFixas;

		public String getNomeDespesa() {
			return nomeDespesa;
		}

		public void setNomeDespesa(String nomeDespesa) {
			this.nomeDespesa = nomeDespesa;
		}

		public Map<Integer, DespesaFixa> getDespesasFixas() {
			return despesasFixas;
		}

		public void setDespesasFixas(Map<Integer, DespesaFixa> despesasFixas) {
			this.despesasFixas = despesasFixas;
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
			return this.nomeDespesa.compareTo(o.getNomeDespesa());
		}
		
		public BigDecimal getTotal(){
			BigDecimal total = BigDecimal.ZERO;
			
			for (Integer mes : despesasFixas.keySet()) {
				total = total.add(despesasFixas.get(mes).getValor());
			}
			
			return total;
		}

	}

}
