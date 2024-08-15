package com.epm.gestepm.modelapi.customer.dto;

import com.epm.gestepm.modelapi.project.dto.Project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID", unique=true, nullable=false, precision=10)
    private Long id;

	@Column(name="NAME", nullable=false, length=128)
    private String name;
	
	@Column(name="MAIN_EMAIL", nullable=true, length=64)
    private String mainEmail;
	
	@Column(name="SECONDARY_EMAIL", nullable=true, length=64)
    private String secondaryEmail;
	
	@OneToOne
	@JoinColumn(name = "PROJECT_ID", referencedColumnName = "id")
	private Project project;

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

	public String getMainEmail() {
		return mainEmail;
	}

	public void setMainEmail(String mainEmail) {
		this.mainEmail = mainEmail;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
