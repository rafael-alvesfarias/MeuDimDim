package br.com.mdd.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name="fixed_expense")
public class FixedExpense extends Expense {
	
	@Column(name = "maturity_day")
	private Integer maturityDay;
	
	@SuppressWarnings("unused")
	FixedExpense() {
		//This constructor shouldn't be used
	}
	
	public FixedExpense(String descricao, BigDecimal valor) {
		super(descricao, valor);
		this.maturityDay = getDueDate().getDayOfMonth();
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
			throw new IllegalArgumentException("Dia de vencimento passada por parâmetro inválida." + vencimento);
		}
		data.withDayOfMonth(vencimento);
		this.setDueDate(data);
		this.maturityDay = vencimento;
	}

	public Integer getMaturityDay() {
		return maturityDay;
	}

	@Override
	public String toString() {
		return "DespesaFixa [descrição=" + getName() + ", vencimento="
				+ maturityDay + ", data de vencimento=" + getDueDate()
				+ ", valor=" + getValue() + "]";
	}

}
