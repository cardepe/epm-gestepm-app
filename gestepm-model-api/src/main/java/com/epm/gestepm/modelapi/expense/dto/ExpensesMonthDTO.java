package com.epm.gestepm.modelapi.expense.dto;

public class ExpensesMonthDTO {

	private Long userId;
	private Double janTotal;
	private Double febTotal;
	private Double marTotal;
	private Double aprTotal;
	private Double mayTotal;
	private Double junTotal;
	private Double julTotal;
	private Double augTotal;
	private Double sepTotal;
	private Double octTotal;
	private Double novTotal;
	private Double decTotal;

	public ExpensesMonthDTO() {

	}
	
	// RentalExpensesMonth DTO Constructor
	public ExpensesMonthDTO(Double janTotal, Double febTotal, Double marTotal, Double aprTotal,
			Double mayTotal, Double junTotal, Double julTotal, Double augTotal, Double sepTotal, Double octTotal,
			Double novTotal, Double decTotal) {
		super();
		this.janTotal = janTotal;
		this.febTotal = febTotal;
		this.marTotal = marTotal;
		this.aprTotal = aprTotal;
		this.mayTotal = mayTotal;
		this.junTotal = junTotal;
		this.julTotal = julTotal;
		this.augTotal = augTotal;
		this.sepTotal = sepTotal;
		this.octTotal = octTotal;
		this.novTotal = novTotal;
		this.decTotal = decTotal;
	}
	
	public ExpensesMonthDTO(Long userId, Double janTotal, Double febTotal, Double marTotal, Double aprTotal,
			Double mayTotal, Double junTotal, Double julTotal, Double augTotal, Double sepTotal, Double octTotal,
			Double novTotal, Double decTotal) {
		super();
		this.userId = userId;
		this.janTotal = janTotal;
		this.febTotal = febTotal;
		this.marTotal = marTotal;
		this.aprTotal = aprTotal;
		this.mayTotal = mayTotal;
		this.junTotal = junTotal;
		this.julTotal = julTotal;
		this.augTotal = augTotal;
		this.sepTotal = sepTotal;
		this.octTotal = octTotal;
		this.novTotal = novTotal;
		this.decTotal = decTotal;
	}
	
	// WorkShare not work with doubles ?? Parse Long (seconds)
	public ExpensesMonthDTO(Long userId, Long janTotal, Long febTotal, Long marTotal, Long aprTotal,
			Long mayTotal, Long junTotal, Long julTotal, Long augTotal, Long sepTotal, Long octTotal,
			Long novTotal, Long decTotal) {
		super();
		this.userId = userId;
		this.janTotal = janTotal.doubleValue();
		this.febTotal = febTotal.doubleValue();
		this.marTotal = marTotal.doubleValue();
		this.aprTotal = aprTotal.doubleValue();
		this.mayTotal = mayTotal.doubleValue();
		this.junTotal = junTotal.doubleValue();
		this.julTotal = julTotal.doubleValue();
		this.augTotal = augTotal.doubleValue();
		this.sepTotal = sepTotal.doubleValue();
		this.octTotal = octTotal.doubleValue();
		this.novTotal = novTotal.doubleValue();
		this.decTotal = decTotal.doubleValue();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getJanTotal() {
		return janTotal;
	}

	public void setJanTotal(Double janTotal) {
		this.janTotal = janTotal;
	}

	public Double getFebTotal() {
		return febTotal;
	}

	public void setFebTotal(Double febTotal) {
		this.febTotal = febTotal;
	}

	public Double getMarTotal() {
		return marTotal;
	}

	public void setMarTotal(Double marTotal) {
		this.marTotal = marTotal;
	}

	public Double getAprTotal() {
		return aprTotal;
	}

	public void setAprTotal(Double aprTotal) {
		this.aprTotal = aprTotal;
	}

	public Double getMayTotal() {
		return mayTotal;
	}

	public void setMayTotal(Double mayTotal) {
		this.mayTotal = mayTotal;
	}

	public Double getJunTotal() {
		return junTotal;
	}

	public void setJunTotal(Double junTotal) {
		this.junTotal = junTotal;
	}

	public Double getJulTotal() {
		return julTotal;
	}

	public void setJulTotal(Double julTotal) {
		this.julTotal = julTotal;
	}

	public Double getAugTotal() {
		return augTotal;
	}

	public void setAugTotal(Double augTotal) {
		this.augTotal = augTotal;
	}

	public Double getSepTotal() {
		return sepTotal;
	}

	public void setSepTotal(Double sepTotal) {
		this.sepTotal = sepTotal;
	}

	public Double getOctTotal() {
		return octTotal;
	}

	public void setOctTotal(Double octTotal) {
		this.octTotal = octTotal;
	}

	public Double getNovTotal() {
		return novTotal;
	}

	public void setNovTotal(Double novTotal) {
		this.novTotal = novTotal;
	}

	public Double getDecTotal() {
		return decTotal;
	}

	public void setDecTotal(Double decTotal) {
		this.decTotal = decTotal;
	}

	public double transformTimeToValue(double hourPrice) {
		
		double totalExpenses = 0.0;
		
		janTotal = (janTotal / 3600) * hourPrice;
		totalExpenses += janTotal;
		
		febTotal = (febTotal / 3600) * hourPrice;
		totalExpenses += febTotal;
		
		marTotal = (marTotal / 3600) * hourPrice;
		totalExpenses += marTotal;
		
		aprTotal = (aprTotal / 3600) * hourPrice;
		totalExpenses += aprTotal;
		
		mayTotal = (mayTotal / 3600) * hourPrice;
		totalExpenses += mayTotal;
		
		junTotal = (junTotal / 3600) * hourPrice;
		totalExpenses += junTotal;
		
		julTotal = (julTotal / 3600) * hourPrice;
		totalExpenses += julTotal;
		
		augTotal = (augTotal / 3600) * hourPrice;
		totalExpenses += augTotal;
		
		sepTotal = (sepTotal / 3600) * hourPrice;
		totalExpenses += sepTotal;
		
		octTotal = (octTotal / 3600) * hourPrice;
		totalExpenses += octTotal;
		
		novTotal = (novTotal / 3600) * hourPrice;
		totalExpenses += novTotal;
		
		decTotal = (decTotal / 3600) * hourPrice;
		totalExpenses += decTotal;
		
		return totalExpenses;
	}
	
	public double transformTimeToValueAndUpdate(double hourPrice, ExpensesMonthDTO expenseMonthDTO) {
		
		double totalExpenses = 0.0;
		
		janTotal = (janTotal / 3600) * hourPrice;
		totalExpenses += janTotal;
		expenseMonthDTO.setJanTotal(expenseMonthDTO.getJanTotal() + janTotal);
		
		febTotal = (febTotal / 3600) * hourPrice;
		totalExpenses += febTotal;
		expenseMonthDTO.setFebTotal(expenseMonthDTO.getFebTotal() + febTotal);

		marTotal = (marTotal / 3600) * hourPrice;
		totalExpenses += marTotal;
		expenseMonthDTO.setMarTotal(expenseMonthDTO.getMarTotal() + marTotal);

		aprTotal = (aprTotal / 3600) * hourPrice;
		totalExpenses += aprTotal;
		expenseMonthDTO.setAprTotal(expenseMonthDTO.getAprTotal() + aprTotal);

		mayTotal = (mayTotal / 3600) * hourPrice;
		totalExpenses += mayTotal;
		expenseMonthDTO.setMayTotal(expenseMonthDTO.getMayTotal() + mayTotal);

		junTotal = (junTotal / 3600) * hourPrice;
		totalExpenses += junTotal;
		expenseMonthDTO.setJunTotal(expenseMonthDTO.getJunTotal() + junTotal);

		julTotal = (julTotal / 3600) * hourPrice;
		totalExpenses += julTotal;
		expenseMonthDTO.setJulTotal(expenseMonthDTO.getJulTotal() + julTotal);

		augTotal = (augTotal / 3600) * hourPrice;
		totalExpenses += augTotal;
		expenseMonthDTO.setAugTotal(expenseMonthDTO.getAugTotal() + augTotal);

		sepTotal = (sepTotal / 3600) * hourPrice;
		totalExpenses += sepTotal;
		expenseMonthDTO.setSepTotal(expenseMonthDTO.getSepTotal() + sepTotal);

		octTotal = (octTotal / 3600) * hourPrice;
		totalExpenses += octTotal;
		expenseMonthDTO.setOctTotal(expenseMonthDTO.getOctTotal() + octTotal);

		novTotal = (novTotal / 3600) * hourPrice;
		totalExpenses += novTotal;
		expenseMonthDTO.setNovTotal(expenseMonthDTO.getNovTotal() + novTotal);

		decTotal = (decTotal / 3600) * hourPrice;
		totalExpenses += decTotal;
		expenseMonthDTO.setDecTotal(expenseMonthDTO.getDecTotal() + decTotal);

		return totalExpenses;
	}

	public void sum(ExpensesMonthDTO expenseMonthDTO) {

		this.setJanTotal(this.getJanTotal() + expenseMonthDTO.getJanTotal());
		this.setFebTotal(this.getFebTotal() + expenseMonthDTO.getFebTotal());
		this.setMarTotal(this.getMarTotal() + expenseMonthDTO.getMarTotal());
		this.setAprTotal(this.getAprTotal() + expenseMonthDTO.getAprTotal());
		this.setMayTotal(this.getMayTotal() + expenseMonthDTO.getMayTotal());
		this.setJunTotal(this.getJunTotal() + expenseMonthDTO.getJunTotal());
		this.setJulTotal(this.getJulTotal() + expenseMonthDTO.getJulTotal());
		this.setAugTotal(this.getAugTotal() + expenseMonthDTO.getAugTotal());
		this.setSepTotal(this.getSepTotal() + expenseMonthDTO.getSepTotal());
		this.setOctTotal(this.getOctTotal() + expenseMonthDTO.getOctTotal());
		this.setNovTotal(this.getNovTotal() + expenseMonthDTO.getNovTotal());
		this.setDecTotal(this.getDecTotal() + expenseMonthDTO.getDecTotal());
	}
}
