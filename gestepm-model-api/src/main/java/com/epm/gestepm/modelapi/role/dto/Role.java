package com.epm.gestepm.modelapi.role.dto;

import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "roles")
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "ROLE", nullable = false, length = 32)
	private String roleName;

	@JsonIgnore
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private List<User> users;

}
