package com.epm.gestepm.modelapi.expensefile.dto;

import com.epm.gestepm.modelapi.expense.dto.Expense;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "personal_expense_file")
public class ExpenseFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personal_expense_file_id", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "name", nullable = false, length = 64)
	private String name;

	@Column(name = "content", nullable = false)
	private byte[] content;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "personal_expense_id", nullable = false)
	private Expense expense;

}
