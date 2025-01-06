package com.epm.gestepm.modelapi.expense.dto;

import com.epm.gestepm.modelapi.expensefile.dto.ExpenseFile;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.personalexpense.dto.PaymentTypeEnumDto;
import com.epm.gestepm.modelapi.personalexpense.dto.PriceTypeEnumDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "personal_expense")
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personal_expense_id", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@ManyToOne(optional=false)
    @JoinColumn(name="personal_expense_sheet_id", nullable=false)
    private ExpenseSheet expenseSheet;

	@Column(name="notice_date", nullable=false)
    private Date date;
	
	@Column(name="start_date", nullable=false)
    private Date startDate;
	
	@Column(name="end_date")
    private Date endDate;
	
	@Column(name="description")
	private String justification;

	@Column(name="quantity", length=5)
	private Double kms;

    @Column(name="amount", nullable=false, length=22)
    private double total;

    @Column(name="price_type", nullable=false)
    private PriceTypeEnumDto priceType;

    @Column(name="payment_type", nullable=false)
    private PaymentTypeEnumDto paymentType;
    
    @OneToMany(mappedBy = "expense")
	private List<ExpenseFile> files;

}
