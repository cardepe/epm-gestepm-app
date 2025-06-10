package com.epm.gestepm.modelapi.deprecated.user.dto;

import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.deprecated.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.deprecated.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.deprecated.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.deprecated.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.deprecated.workshare.WorkShare;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "name", nullable = false, length = 32)
	private String name;

	@Column(name = "surnames", nullable = false, length = 64)
	private String surnames;

	@Column(name = "email", unique = true, nullable = false, length = 256)
	private String email;

	@Column(name = "password", nullable = false, length = 320)
	private String password;

	@ManyToOne
	@JoinColumn(name = "activity_center_id", nullable = false)
	private ActivityCenter activityCenter;

	@Column(name = "state", nullable = false, length = 1)
	private Integer state;

	@Column(name = "signing_id", nullable = false, length = 11)
	private Long signingId;

	@Column(name = "forum_username", unique = true, length = 32)
	private String username;

	@Column(name = "forum_password", length = 320)
	private String forumPassword;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@ManyToOne
	@JoinColumn(name = "level_id")
	private SubRole subRole;
	
	@Column(name = "working_hours", nullable = false, length = 2)
	private Double workingHours;

	@Column(name = "current_year_holidays_count", nullable = false, length = 2)
	private Integer currentYearHolidaysCount;

	@Column(name = "last_year_holidays_count", nullable = false, length = 2)
	private Integer lastYearHolidaysCount;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<InterventionShare> interventionShares;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<InterventionPrShare> interventionPrShares;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<DisplacementShare> displacementShares;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<WorkShare> workShares;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<PersonalSigning> personalSignings;
	
	@ManyToMany
	@JoinTable(name = "users_projects", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "PROJECT_ID"))
	private List<Project> projects;
	
	@ManyToMany
	@JoinTable(name = "project_bosses", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "PROJECT_ID"))
	private Set<Project> bossProjects;

	@OneToMany(mappedBy = "user")
	private List<UserHoliday> userHolidays;
	
	@ManyToMany(mappedBy = "responsables")
	private List<Project> responsableProjects;
	
	@OneToMany(mappedBy = "firstTechnical", fetch = FetchType.LAZY)
	private List<InterventionSubShare> firstTechInterventions;
	
	@OneToMany(mappedBy = "secondTechnical", fetch = FetchType.LAZY)
	private List<InterventionSubShare> secondTechInterventions;

	@OneToMany(mappedBy = "secondTechnical", fetch = FetchType.LAZY)
	private List<InterventionPrShare> secondTechPrInterventions;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<ExpenseSheet> expenseSheets;

	public String getFullName() {
		final StringBuilder builder = new StringBuilder();

		builder.append(this.name);

		if (StringUtils.isNoneBlank(this.surnames)) {
			builder.append(" ").append(this.surnames);
		}

		return builder.toString();
	}
}
