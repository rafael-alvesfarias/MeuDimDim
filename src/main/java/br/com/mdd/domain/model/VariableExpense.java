package br.com.mdd.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name="variable_expense")
public class VariableExpense extends Expense {
	
	//TODO Uma despesa variável pode ter um conjunto de despesas variáveis
	
	@SuppressWarnings("unused")
	VariableExpense() {
		//This constructor shouldn't be used
	}
	
	public VariableExpense(String descricao, BigDecimal valor) {
		super(descricao, valor);
	}

	public VariableExpense(String descricao, BigDecimal valor, LocalDate dataVencimento) {
		super(descricao, valor, dataVencimento);
	}

	@Override
	public String toString() {
		return "DespesaVariavel [descrição=" + getName() + ", data de vencimento="
				+ getDueDate() + ", valor=" + getValue() + "]";
	}
	
	@Override
	public VariableExpense clone() {
		VariableExpense e = (VariableExpense) super.clone();
		return e;
	}

}
