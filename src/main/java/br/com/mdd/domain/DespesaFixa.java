package br.com.mdd.domain;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class DespesaFixa implements Comparable<DespesaFixa>{
	
	private String descricao;
	
	private Integer vencimento;
	
	private LocalDate dataVencimento;
	
	private BigDecimal valor;
	
	public DespesaFixa(String descricao, BigDecimal valor) {
		LocalDate hoje = LocalDate.now();
		this.descricao = descricao;
		this.dataVencimento = hoje;
		this.vencimento = hoje.getDayOfMonth();
		this.valor = valor;
	}

	public DespesaFixa(String descricao, BigDecimal valor, LocalDate dataVencimento) {
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.vencimento = dataVencimento.getDayOfMonth();
		this.valor = valor;
	}

	public DespesaFixa(String descricao, BigDecimal valor, Integer vencimento) {
		LocalDate data = LocalDate.now();
		Integer ultimoDiaDoMesAtual = data.dayOfMonth().getMaximumValue();
		if(vencimento < 0 || vencimento > ultimoDiaDoMesAtual){
			throw new IllegalArgumentException("Dia de vencimento passada por parâmetro inválida." + vencimento);
		}
		data.withDayOfMonth(vencimento);
		this.descricao = descricao;
		this.dataVencimento = data;
		this.vencimento = vencimento;
		this.valor = valor;
	}

	public Integer getVencimento() {
		return vencimento;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public BigDecimal getValor(){
		return valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataVencimento == null) ? 0 : dataVencimento.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		DespesaFixa other = (DespesaFixa) obj;
		if (dataVencimento == null) {
			if (other.dataVencimento != null)
				return false;
		} else if (!dataVencimento.equals(other.dataVencimento))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DespesaFixa [descrição=" + descricao + ", vencimento="
				+ vencimento + ", data de vencimento=" + dataVencimento
				+ ", valor=" + valor + "]";
	}

	@Override
	public int compareTo(DespesaFixa o) {
		return this.getDescricao().compareTo(o.getDescricao());
	}

}
