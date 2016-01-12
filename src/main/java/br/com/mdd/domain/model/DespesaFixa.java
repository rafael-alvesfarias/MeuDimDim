package br.com.mdd.domain.model;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class DespesaFixa extends Despesa{
	
	private Integer vencimento;
	
	public DespesaFixa() {
		this(null, BigDecimal.ZERO);
	}
	
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
			throw new IllegalArgumentException("Dia de vencimento passada por par�metro inv�lida." + vencimento);
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
		return "DespesaFixa [descri��o=" + getDescricao() + ", vencimento="
				+ vencimento + ", data de vencimento=" + getDataLancamento()
				+ ", valor=" + getValor() + "]";
	}

}
