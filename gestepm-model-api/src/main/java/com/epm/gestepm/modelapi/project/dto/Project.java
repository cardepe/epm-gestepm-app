package com.epm.gestepm.modelapi.project.dto;

import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.customer.dto.Customer;
import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.family.dto.Family;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.materialrequired.dto.MaterialRequired;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@Column(name = "NAME", nullable = false, precision = 64)
	private String name;
	
	@Column(name = "STATION", nullable = false)
    private int station;
	
	@OneToMany(mappedBy = "station")
	private List<Project> projects;
	
	@ManyToMany
	@JoinTable(name = "projects_responsables", joinColumns = @JoinColumn(name = "PROJECT_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private List<User> responsables;
	
	@Column(name = "OBJECTIVE_COST", nullable = false)
	private Double objectiveCost;
	
	@Column(name = "START_DATE", nullable = false)
	private Date startDate;

	@Column(name = "OBJECTIVE_DATE", nullable = false)
	private Date objectiveDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACTIVITY_CENTER_ID", referencedColumnName = "activity_center_id", nullable = false)
	private ActivityCenter activityCenter;
	
	@Column(name = "FORUM_ID")
	private Long forumId;

	@OneToMany(mappedBy = "project")
	private List<InterventionShare> interventionShares;
	
	@OneToMany(mappedBy = "project")
	private List<InterventionPrShare> interventionPrShares;
	
	@OneToMany(mappedBy = "project")
	private List<DisplacementShare> displacementShares;
	
	@OneToMany(mappedBy = "project")
	private List<UserSigning> userSignings;
	
	@OneToMany(mappedBy = "project")
	private List<WorkShare> workShares;
	
	@ManyToMany(mappedBy = "projects")
	private List<User> users;

	@ManyToMany(mappedBy = "projects")
	private List<Displacement> displacements;
	
	@OneToMany(mappedBy = "project")
	private List<Family> families;
	
	@OneToMany(mappedBy = "project")
	private List<MaterialRequired> materialsRequired;

	@ManyToMany(mappedBy = "bossProjects")
	private List<User> bossUsers;

	@OneToMany(mappedBy = "project")
	private List<ExpenseSheet> expenseSheets;
	
	@OneToOne(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Customer customer;
	
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

	public int getStation() {
		return station;
	}

	public void setStation(int station) {
		this.station = station;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<User> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<User> responsables) {
		this.responsables = responsables;
	}

	public Double getObjectiveCost() {
		return objectiveCost;
	}

	public void setObjectiveCost(Double objectiveCost) {
		this.objectiveCost = objectiveCost;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getObjectiveDate() {
		return objectiveDate;
	}

	public void setObjectiveDate(Date objectiveDate) {
		this.objectiveDate = objectiveDate;
	}

	public ActivityCenter getActivityCenter() {
		return activityCenter;
	}

	public void setActivityCenter(ActivityCenter activityCenter) {
		this.activityCenter = activityCenter;
	}

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	public List<InterventionShare> getInterventionShares() {
		return interventionShares;
	}

	public void setInterventionShares(List<InterventionShare> interventionShares) {
		this.interventionShares = interventionShares;
	}
	
	public List<InterventionPrShare> getInterventionPrShares() {
		return interventionPrShares;
	}

	public void setInterventionPrShares(List<InterventionPrShare> interventionPrShares) {
		this.interventionPrShares = interventionPrShares;
	}

	public List<DisplacementShare> getDisplacementShares() {
		return displacementShares;
	}

	public void setDisplacementShares(List<DisplacementShare> displacementShares) {
		this.displacementShares = displacementShares;
	}

	public List<WorkShare> getWorkShares() {
		return workShares;
	}

	public void setWorkShares(List<WorkShare> workShares) {
		this.workShares = workShares;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<UserSigning> getUserSignings() {
		return userSignings;
	}

	public void setUserSignings(List<UserSigning> userSignings) {
		this.userSignings = userSignings;
	}

	public List<Displacement> getDisplacements() {
		return displacements;
	}

	public void setDisplacements(List<Displacement> displacements) {
		this.displacements = displacements;
	}

	public List<Family> getFamilies() {
		return families;
	}

	public void setFamilies(List<Family> families) {
		this.families = families;
	}

	public List<MaterialRequired> getMaterialsRequired() {
		return materialsRequired;
	}

	public void setMaterialsRequired(List<MaterialRequired> materialsRequired) {
		this.materialsRequired = materialsRequired;
	}

	public List<User> getBossUsers() {
		return bossUsers;
	}

	public void setBossUsers(List<User> bossUsers) {
		this.bossUsers = bossUsers;
	}

	public List<ExpenseSheet> getExpenseSheets() {
		return expenseSheets;
	}

	public void setExpenseSheets(List<ExpenseSheet> expenseSheets) {
		this.expenseSheets = expenseSheets;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
