package com.epm.gestepm.modelapi.subrole.dto;

import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;
import com.epm.gestepm.modelapi.userold.dto.User;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sub_roles")
public class SubRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "ROL", unique = true, nullable = false, length = 32)
	private String rol;

	@Column(name = "PRICE", nullable = false, length = 22)
	private Double price;

	@OneToMany(mappedBy = "subRole", fetch = FetchType.LAZY)
	private List<User> users;

	@ManyToMany(mappedBy = "subRoles")
	private List<SubFamily> subFamilies;
	
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<SubFamily> getSubFamilies() {
		return subFamilies;
	}

	public void setSubFamilies(List<SubFamily> subFamilies) {
		this.subFamilies = subFamilies;
	}
}
