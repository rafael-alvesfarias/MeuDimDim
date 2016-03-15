package br.com.mdd.presentation.view.model;

import java.math.BigDecimal;
import java.util.Set;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;

public class ExpenseViewModel {
	
	private Integer id;
	
	private String descricao;
	
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal valor;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataLancamento;
	
	private String categoria = new String();
	
	private Boolean despesaFixa;
	
	private Boolean pago;
	
	private Set<Category> categorias;
	
	public static final ExpenseViewModel fromExpense(Expense expense) {
		ExpenseViewModel e = new ExpenseViewModel();
		e.setCategoria(expense.getCategory().getName());
		e.setDataLancamento(expense.getDueDate());
		e.setDescricao(expense.getName());
		e.setDespesaFixa(true);
		e.setId(expense.getId());
		e.setPago(expense.getPaid());
		e.setValor(expense.getValue());
		
		if(expense instanceof FixedExpense) {
			e.setDespesaFixa(true);			
		}
		
		return e;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Set<Category> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<Category> categorias) {
		this.categorias = categorias;
	}

	public Boolean getDespesaFixa() {
		return despesaFixa;
	}

	public void setDespesaFixa(Boolean despesaFixa) {
		this.despesaFixa = despesaFixa;
	}

	public Boolean getPago() {
		return pago;
	}

	public void setPago(Boolean pago) {
		this.pago = pago;
	}

}
