package br.com.mdd.domain.model;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.Locale;

import javax.persistence.Column;
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
@Table(name = "entry")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Entry implements Comparable<Entry>, Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column
	private Integer id;

	@Column
	private String name;

	@Column
	private BigDecimal value;

	//Due date significa data vencimento
	@Column
	private LocalDate dueDate;
	
	//Data do lançamento
	@Column
	private LocalDate entryDate;
	
	private final int factor;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Account account;
	
	Entry() {
		// this constructor shoudn't be used
		this.factor = 1;
	}
	
	public void post(Account account) {
		this.setEntryDate(new LocalDate());
		this.setAccount(account);
		if (this.getFactor() == 1) {
			account.deposit(this.getValue());
		} else if (this.getFactor() == -1) {
			account.withdraw(this.getValue());
		} else {
			throw new IllegalStateException("factor should be 1 or -1");
		}
	}
	
	public boolean isPosted() {
		return this.entryDate != null;
	}

	public Entry(String name, BigDecimal value, LocalDate dueDate, int factor) {
		this.name = name;
		this.value = value;
		this.dueDate = dueDate;
		this.factor = factor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public LocalDate getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	public int getFactor() {
		return factor;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Entry other = (Entry) obj;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
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
	public int compareTo(Entry o) {
		final Collator collator = Collator.getInstance(new Locale("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);

		return collator.compare(this.getName(), o.getName());
	}
	
	@Override
	public Entry clone() {
		try {
			Entry e = (Entry) super.clone();
			return e;
		} catch (CloneNotSupportedException ex) {
			throw new RuntimeException(ex);
		}
	}

}
