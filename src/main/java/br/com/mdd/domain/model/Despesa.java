package br.com.mdd.domain.model;

import java.math.BigDecimal;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class Despesa implements Comparable<Despesa> {
	
	private String descricao;
	
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal valor;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataLancamento;

	public Despesa(String descricao, BigDecimal valor, LocalDate dataLancamento) {
		this.descricao = descricao;
		this.valor = valor;
		this.dataLancamento = dataLancamento;
	}

	public Despesa(String descricao, BigDecimal valor) {
		this(descricao, valor, LocalDate.now());
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataLancamento == null) ? 0 : dataLancamento.hashCode());
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
		Despesa other = (Despesa) obj;
		if (dataLancamento == null) {
			if (other.dataLancamento != null)
				return false;
		} else if (!dataLancamento.equals(other.dataLancamento))
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
	public int compareTo(Despesa o) {
		return this.getDescricao().compareTo(o.getDescricao());
	}

}
