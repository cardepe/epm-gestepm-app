package com.epm.gestepm.modelapi.deprecated.project.dto;

public class ProjectListDTO {

	private Long id;
	private String name;
	private Integer station;
	private String customerEmail;

	/**
	 * findStationDTOs()
	 */
	public ProjectListDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * findAllProjectsDTOs(), findBossProjectsDTOByUserId(Long), findProjectsDTOByUserId(Long)
	 */
	public ProjectListDTO(Long id, String name, Integer station) {
		super();
		this.id = id;
		this.name = name;
		this.station = station;
	}
	
	public ProjectListDTO(Long id, String name, Integer station, String customerEmail) {
		super();
		this.id = id;
		this.name = name;
		this.station = station;
		this.customerEmail = customerEmail;
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

	public Integer getStation() {
		return station;
	}

	public void setStation(Integer station) {
		this.station = station;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
