package com.epm.gestepm.modelapi.activitycenter.dto;

import com.epm.gestepm.modelapi.country.dto.Country;
import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "activity_center")
public class ActivityCenter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACTIVITY_CENTER_ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID", nullable = false)
	private Country country;
	
	@Column(name = "NAME", nullable = false, length = 32)
	private String name;

	@OneToMany(mappedBy = "activityCenter")
	private List<Displacement> displacements;
	
	@OneToMany(mappedBy = "activityCenter")
	private List<Holiday> holidays;
	
	@OneToMany(mappedBy = "activityCenter", fetch = FetchType.LAZY)
	private List<User> users;
	
	@OneToMany(mappedBy = "activityCenter")
	private List<Project> projects;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Displacement> getDisplacements() {
		return displacements;
	}

	public void setDisplacements(List<Displacement> displacements) {
		this.displacements = displacements;
	}

	public List<Holiday> getHolidays() {
		return holidays;
	}

	public void setHolidays(List<Holiday> holidays) {
		this.holidays = holidays;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

}
