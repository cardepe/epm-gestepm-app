package com.epm.gestepm.modelapi.expensesheet.dto;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.expense.dto.ExpenseDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class ExpenseSheetDTO {

	private Long id;

	private String name;

	@DateTimeFormat(iso = ISO.DATE)
	private Date creationDate;

	private Long project;
	
	private Long user;

	private List<ExpenseDTO> expenses;

	public ExpenseSheetDTO() {

	}

	public ExpenseSheetDTO(Long id, String name, Date creationDate, Long project, Long user, List<ExpenseDTO> expenses) {
		super();
		this.id = id;
		this.name = name;
		this.creationDate = creationDate;
		this.project = project;
		this.user = user;
		this.expenses = expenses;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getProject() {
		return project;
	}

	public void setProject(Long project) {
		this.project = project;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public List<ExpenseDTO> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<ExpenseDTO> expenses) {
		this.expenses = expenses;
	}

}
