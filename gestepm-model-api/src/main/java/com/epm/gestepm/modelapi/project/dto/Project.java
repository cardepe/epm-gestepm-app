package com.epm.gestepm.modelapi.project.dto;

import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.customer.dto.Customer;
import com.epm.gestepm.modelapi.deprecated.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.deprecated.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.family.dto.Family;
import com.epm.gestepm.modelapi.deprecated.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.materialrequired.dto.MaterialRequired;
import com.epm.gestepm.modelapi.userold.dto.User;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
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

	@Column(name = "teleworking")
	private boolean teleworking;

	@OneToMany(mappedBy = "project")
	private List<InterventionShare> interventionShares;
	
	@OneToMany(mappedBy = "project")
	private List<InterventionPrShare> interventionPrShares;
	
	@OneToMany(mappedBy = "project")
	private List<DisplacementShare> displacementShares;
	
	@OneToMany(mappedBy = "project")
	private List<WorkShare> workShares;
	
	@ManyToMany(mappedBy = "projects")
	private List<User> users;
	
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

}
