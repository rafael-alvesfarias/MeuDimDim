package br.com.mdd.domain.model;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name="expense")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Expense implements Comparable<Expense> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "expense_id")
	private Integer id;
	
	private String name;
	
	private BigDecimal value;
	
	@Column(name="maturity_date")
	private LocalDate maturityDate;
	
	@ManyToOne
	private Category category;
	
	private Boolean paid;
	
	@ManyToOne
	private User user;
	
	Expense() {
		//Construtor necess�rio para framework
	}

	public Expense(String descricao, BigDecimal valor, LocalDate dataLancamento) {
		this.name = descricao;
		this.value = valor;
		this.maturityDate = dataLancamento;
	}

	public Expense(String descricao, BigDecimal valor) {
		this(descricao, valor, LocalDate.now());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String descricao) {
		this.name = descricao;
	}

	public LocalDate getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(LocalDate dataLancamento) {
		this.maturityDate = dataLancamento;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((maturityDate == null) ? 0 : maturityDate.hashCode());
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Expense other = (Expense) obj;
		if (maturityDate == null) {
			if (other.maturityDate != null)
				return false;
		} else if (!maturityDate.equals(other.maturityDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Expense o) {
		final Collator collator = Collator.getInstance(new Locale("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);
		
		return collator.compare(this.name, o.getName());
	}

}
