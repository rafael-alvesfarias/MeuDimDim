package br.com.mdd.domain.model;

import java.util.List;

public class CreditCard {
	
	private Integer id;
	
	private String name;
	
	private String description;
	
	private Integer maturityDay;
	
	private String brand;
	
	private List<CreditCardStatement> statements;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMaturityDay() {
		return maturityDay;
	}

	public void setMaturityDay(Integer maturityDay) {
		this.maturityDay = maturityDay;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<CreditCardStatement> getStatements() {
		return statements;
	}

	public void setStatements(List<CreditCardStatement> statements) {
		this.statements = statements;
	}

}
