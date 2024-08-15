package com.epm.gestepm.modelapi.absencetype.dto;

import com.epm.gestepm.modelapi.userabsence.dto.UserAbsence;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "absence_types")
public class AbsenceType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "NAME", nullable = false, length = 32)
	private String name;

	@OneToMany(mappedBy = "absenceType")
	private List<UserAbsence> usersAbsences;
	
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

	public List<UserAbsence> getUsersAbsences() {
		return usersAbsences;
	}

	public void setUsersAbsences(List<UserAbsence> usersAbsences) {
		this.usersAbsences = usersAbsences;
	}
}
