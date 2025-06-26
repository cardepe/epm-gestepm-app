package com.epm.gestepm.modelapi.deprecated.materialrequired.dto;

import com.epm.gestepm.modelapi.deprecated.project.dto.Project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "materials_required")
public class MaterialRequired {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "PROJECT_ID")
	private Project project;
	
	@Column(name="NAME_ES", nullable=false, length=128)
    private String nameES;
	
	@Column(name="NAME_FR", nullable=false, length=128)
    private String nameFR;
	
	@Column(name="REQUIRED", nullable=false, length=1)
    private Integer required;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getNameES() {
		return nameES;
	}

	public void setNameES(String nameES) {
		this.nameES = nameES;
	}

	public String getNameFR() {
		return nameFR;
	}

	public void setNameFR(String nameFR) {
		this.nameFR = nameFR;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}
}
