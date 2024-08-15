package com.epm.gestepm.modelapi.role.dto;

public class RoleDTO {

	private Long id;
	private String name;
	private Double price;
	
	public RoleDTO() {
		
	}

	public RoleDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public RoleDTO(String name, Double price) {
		super();
		this.name = name;
		this.price = price;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
