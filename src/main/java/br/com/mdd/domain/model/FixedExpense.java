package br.com.mdd.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name="fixed_expense")
public class FixedExpense extends Expense{
	
	@Column(name = "maturity_day")
	private Integer maturityDay;
	
	@SuppressWarnings("unused")
	private FixedExpense() {
		//This constructor shouldn't be used
	}
	
	public FixedExpense(String descricao, BigDecimal valor) {
		super(descricao, valor);
		this.maturityDay = getMaturityDate().getDayOfMonth();
	}

	public FixedExpense(String descricao, BigDecimal valor, LocalDate dataVencimento) {
		super(descricao, valor, dataVencimento);
		this.maturityDay = dataVencimento.getDayOfMonth();
	}

	public FixedExpense(String descricao, BigDecimal valor, Integer vencimento) {
		super(descricao, valor);
		LocalDate data = LocalDate.now();
		Integer ultimoDiaDoMesAtual = data.dayOfMonth().getMaximumValue();
		if(vencimento < 0 || vencimento > ultimoDiaDoMesAtual){
			throw new IllegalArgumentException("Dia de vencimento passada por par�metro inv�lida." + vencimento);
		}
		data.withDayOfMonth(vencimento);
		this.setMaturityDate(data);
		this.maturityDay = vencimento;
	}

	public Integer getMaturityDay() {
		return maturityDay;
	}

	@Override
	public String toString() {
		return "DespesaFixa [descri��o=" + getName() + ", vencimento="
				+ maturityDay + ", data de vencimento=" + getMaturityDate()
				+ ", valor=" + getValue() + "]";
	}

}
