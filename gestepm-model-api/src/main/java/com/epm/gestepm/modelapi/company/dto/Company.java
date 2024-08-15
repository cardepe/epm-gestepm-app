package com.epm.gestepm.modelapi.company.dto;

import javax.persistence.*;

@Entity
@Table(name = "companies")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "NAME", nullable = false, length = 64)
	private String name;
	
	@Column(name = "CUSTOMER_ID", nullable = false, length = 11)
	private Long customerId;
	
	@Column(name = "CIF", nullable = false, length = 14)
	private String cif;
	
	@Column(name = "DIRECTION", nullable = false, length = 128)
	private String direction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
