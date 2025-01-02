package com.epm.gestepm.modelapi.deprecated.country.dto;

import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "country")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COUNTRY_ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "NAME", unique = true, nullable = false, length = 32)
	private String name;
	
	@Column(name = "TAG", unique = true, nullable = false, length = 2)
	private String tag;

	@OneToMany(mappedBy = "country")
	private List<ActivityCenter> activityCenters;
	
	@OneToMany(mappedBy = "country")
	private List<Holiday> holidays;

}
