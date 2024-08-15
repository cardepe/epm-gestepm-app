package com.epm.gestepm.modelapi.expense.dto;

import com.epm.gestepm.modelapi.expensefile.dto.ExpenseFile;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.paymenttype.dto.PaymentType;
import com.epm.gestepm.modelapi.pricetype.dto.PriceType;

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

@Entity
@Table(name = "expenses")
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@ManyToOne(optional=false)
    @JoinColumn(name="EXPENSE_SHEET_ID", nullable=false)
    private ExpenseSheet expenseSheet;

	@Column(name="DATE", nullable=false)
    private Date date;
	
	@Column(name="START_DATE", nullable=false)
    private Date startDate;
	
	@Column(name="END_DATE")
    private Date endDate;
	
	@Column(name="JUSTIFICATION")
	private String justification;

	@Column(name="KMS", length=5)
	private Double kms;

    @Column(name="TOTAL", nullable=false, length=22)
    private double total;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="PRICE_TYPE", nullable=false)
    private PriceType priceType;
    
    @Column(name="PRICE_CUSTOM")
	private String priceCustom;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="PAYMENT_TYPE", nullable=false)
    private PaymentType paymentType;
    
    @OneToMany(mappedBy = "expense")
	private List<ExpenseFile> files;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExpenseSheet getExpenseSheet() {
		return expenseSheet;
	}

	public void setExpenseSheet(ExpenseSheet expenseSheet) {
		this.expenseSheet = expenseSheet;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getKms() {
		return kms;
	}

	public void setKms(Double kms) {
		this.kms = kms;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public PriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}

	public String getPriceCustom() {
		return priceCustom;
	}

	public void setPriceCustom(String priceCustom) {
		this.priceCustom = priceCustom;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public List<ExpenseFile> getFiles() {
		return files;
	}

	public void setFiles(List<ExpenseFile> files) {
		this.files = files;
	}
}
