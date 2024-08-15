package com.epm.gestepm.modelapi.role.dto;

import com.google.gson.annotations.SerializedName;

public class RoleTableDTO {

	@SerializedName("sr_id")
	private Long id;

	@SerializedName("sr_rol")
	private String rol;

	@SerializedName("sr_price")
	private Double price;

	public RoleTableDTO(Long id, String rol, Double price) {
		super();
		this.id = id;
		this.rol = rol;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
