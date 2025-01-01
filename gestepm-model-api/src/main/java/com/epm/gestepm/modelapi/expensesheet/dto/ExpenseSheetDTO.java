package com.epm.gestepm.modelapi.expensesheet.dto;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.expense.dto.ExpenseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSheetDTO {

	private Long id;

	private String name;

	@DateTimeFormat(iso = ISO.DATE)
	private Date creationDate;

	private Long project;
	
	private Long user;

	private List<ExpenseDTO> expenses;

}
