package br.com.mdd.domain;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class DespesaFixa extends Despesa implements Comparable<DespesaFixa>{
	
	private Integer vencimento;
	
	public DespesaFixa(String descricao, BigDecimal valor) {
		super(descricao, valor);
		this.vencimento = getDataLancamento().getDayOfMonth();
	}

	public DespesaFixa(String descricao, BigDecimal valor, LocalDate dataVencimento) {
		super(descricao, valor, dataVencimento);
		this.vencimento = dataVencimento.getDayOfMonth();
	}

	public DespesaFixa(String descricao, BigDecimal valor, Integer vencimento) {
		super(descricao, valor);
		LocalDate data = LocalDate.now();
		Integer ultimoDiaDoMesAtual = data.dayOfMonth().getMaximumValue();
		if(vencimento < 0 || vencimento > ultimoDiaDoMesAtual){
			throw new IllegalArgumentException("Dia de vencimento passada por parâmetro inválida." + vencimento);
		}
		data.withDayOfMonth(vencimento);
		this.setDataLancamento(data);
		this.vencimento = vencimento;
	}

	public Integer getVencimento() {
		return vencimento;
	}

	@Override
	public String toString() {
		return "DespesaFixa [descrição=" + getDescricao() + ", vencimento="
				+ vencimento + ", data de vencimento=" + getDataLancamento()
				+ ", valor=" + getValor() + "]";
	}

	@Override
	public int compareTo(DespesaFixa o) {
		return this.getDescricao().compareTo(o.getDescricao());
	}

}
