package com.epm.gestepm.modelapi.holiday.dto;

import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.deprecated.country.dto.Country;
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
@Table(name = "holiday")
public class Holiday {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "holiday_id", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "name", nullable = false, length = 64)
	private String name;

	@Column(name = "day", nullable = false, precision = 10)
	private Integer day;

	@Column(name = "month", nullable = false, precision = 10)
	private Integer month;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "country_id")
	private Country country;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "activity_center_id")
	private ActivityCenter activityCenter;

}
