package com.epm.gestepm.modelapi.deprecated.expensesheet.dto;

import com.epm.gestepm.modelapi.deprecated.expense.dto.Expense;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetStatusEnumDto;
import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Data
@Entity
@Table(name = "personal_expense_sheet")
public class ExpenseSheet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personal_expense_sheet_id", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "created_at", nullable = false)
	private Date creationDate;

	@Column(name = "description", nullable = false, length = 64)
	private String name;

	@ManyToOne(optional = false)
	@JoinColumn(name = "created_by", nullable = false)
	private User user;

	@ManyToOne(optional = false)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
    
    @Column(name="status")
	@Enumerated(EnumType.STRING)
    private PersonalExpenseSheetStatusEnumDto status;
    
    @Column(name="observations")
    private String observations;
    
    @OneToMany(mappedBy = "expenseSheet", fetch = FetchType.LAZY)
	private List<Expense> expenses;

}
