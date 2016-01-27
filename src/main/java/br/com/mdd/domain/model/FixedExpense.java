package br.com.mdd.domain.model;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class FixedExpense extends Expense{
	
	private Integer vencimento;
	
	public FixedExpense() {
		this(null, BigDecimal.ZERO);
	}
	
	public FixedExpense(String descricao, BigDecimal valor) {
		super(descricao, valor);
		this.vencimento = getMaturityDate().getDayOfMonth();
	}

	public FixedExpense(String descricao, BigDecimal valor, LocalDate dataVencimento) {
		super(descricao, valor, dataVencimento);
		this.vencimento = dataVencimento.getDayOfMonth();
	}

	public FixedExpense(String descricao, BigDecimal valor, Integer vencimento) {
		super(descricao, valor);
		LocalDate data = LocalDate.now();
		Integer ultimoDiaDoMesAtual = data.dayOfMonth().getMaximumValue();
		if(vencimento < 0 || vencimento > ultimoDiaDoMesAtual){
			throw new IllegalArgumentException("Dia de vencimento passada por parâmetro inválida." + vencimento);
		}
		data.withDayOfMonth(vencimento);
		this.setMaturityDate(data);
		this.vencimento = vencimento;
	}

	public Integer getVencimento() {
		return vencimento;
	}

	@Override
	public String toString() {
		return "DespesaFixa [descrição=" + getName() + ", vencimento="
				+ vencimento + ", data de vencimento=" + getMaturityDate()
				+ ", valor=" + getValue() + "]";
	}

}
