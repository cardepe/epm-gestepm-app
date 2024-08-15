package com.epm.gestepm.modelapi.expensesheet.dto;

import com.epm.gestepm.modelapi.expense.dto.Expense;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "expense_sheets")
public class ExpenseSheet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "CREATION_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "NAME", nullable = false, length = 64)
	private String name;

	@ManyToOne(optional = false)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@ManyToOne(optional = false)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Project project;
    
    @Column(name="STATUS")
    private String status;
    
    @Column(name="OBSERVATIONS")
    private String observations;
    
    @OneToMany(mappedBy = "expenseSheet", fetch = FetchType.LAZY)
	private List<Expense> expenses;

	public ExpenseSheet() {
		super();
	}

	public ExpenseSheet(Long id, Date creationDate, String name, User user, Project project, String status) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.name = name;
		this.user = user;
		this.project = project;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

}
