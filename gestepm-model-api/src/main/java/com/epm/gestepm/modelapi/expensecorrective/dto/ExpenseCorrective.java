package com.epm.gestepm.modelapi.expensecorrective.dto;

import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "expense_correctives")
public class ExpenseCorrective {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Project project;

	@Column(name = "CREATION_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "COST", nullable = false)
	private double cost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}
