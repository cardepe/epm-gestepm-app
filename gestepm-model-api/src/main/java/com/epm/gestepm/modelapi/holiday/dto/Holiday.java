package com.epm.gestepm.modelapi.holiday.dto;

import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.deprecated.country.dto.Country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "holidays")
public class Holiday {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "NAME", nullable = false, length = 64)
	private String name;

	@Column(name = "DAY", nullable = false, precision = 10)
	private Integer day;

	@Column(name = "MONTH", nullable = false, precision = 10)
	private Integer month;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "COUNTRY_ID")
	private Country country;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "ACTIVITY_CENTER_ID")
	private ActivityCenter activityCenter;
	
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

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public ActivityCenter getActivityCenter() {
		return activityCenter;
	}

	public void setActivityCenter(ActivityCenter activityCenter) {
		this.activityCenter = activityCenter;
	}
}
