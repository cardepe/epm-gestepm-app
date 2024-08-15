package com.epm.gestepm.modelapi.customer.dto;

public class CustomerDTO {

	private String customerName;
	private String mainEmail;
	private String secondaryEmail;
	
	public CustomerDTO() {
		
	}
	
	public CustomerDTO(String customerName, String mainEmail, String secondaryEmail) {
		super();
		this.customerName = customerName;
		this.mainEmail = mainEmail;
		this.secondaryEmail = secondaryEmail;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMainEmail() {
		return mainEmail;
	}

	public void setMainEmail(String mainEmail) {
		this.mainEmail = mainEmail;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}
}
