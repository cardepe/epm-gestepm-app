package com.epm.gestepm.modelapi.deprecated.expense.dto;

public class ExpenseUserValidateDTO {

	private Long id;
	private String name;
	private Long expenseSheetsCount;
	private String expenseType;
	
	public ExpenseUserValidateDTO() {
		
	}

	public ExpenseUserValidateDTO(Long id, String name, Long expenseSheetsCount) {
		super();
		this.id = id;
		this.name = name;
		this.expenseSheetsCount = expenseSheetsCount;
		this.expenseType = "users";
	}
	
	public ExpenseUserValidateDTO(Long id, String name, Long expenseSheetsCount, String expenseType) {
		super();
		this.id = id;
		this.name = name;
		this.expenseSheetsCount = expenseSheetsCount;
		this.expenseType = expenseType;
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

	public Long getExpenseSheetsCount() {
		return expenseSheetsCount;
	}

	public void setExpenseSheetsCount(Long expenseSheetsCount) {
		this.expenseSheetsCount = expenseSheetsCount;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
}
