package com.epm.gestepm.model.project.service.mapper;

import java.util.ArrayList;
import java.util.List;

import com.epm.gestepm.modelapi.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.dto.ProjectDTO;
import com.epm.gestepm.modelapi.user.dto.User;

public class ProjectMapper {

	private ProjectMapper() {
		
	}
	
	public static Project mapDTOToProject(ProjectDTO projectDTO, List<User> responsables, ActivityCenter activityCenter) {
		
		Project project = new Project();
		
		project.setName(projectDTO.getProjectName());
		project.setResponsables(responsables);
		project.setObjectiveCost(projectDTO.getObjectiveCost());
		project.setStartDate(projectDTO.getStartDate());
		project.setObjectiveDate(projectDTO.getObjectiveDate());
		project.setActivityCenter(activityCenter);
		project.setStation(projectDTO.getStation());
		project.setForumId(projectDTO.getForumId());
		
		return project;
	}
	
	public static Project copyProject(Project projectCopy) {
		
		Project project = new Project();
		
		List<User> responsables = new ArrayList<>(projectCopy.getResponsables());
		
		project.setName(projectCopy.getName());
		project.setResponsables(responsables);
		project.setObjectiveCost(projectCopy.getObjectiveCost());
		project.setStartDate(projectCopy.getStartDate());
		project.setObjectiveDate(projectCopy.getObjectiveDate());
		project.setActivityCenter(projectCopy.getActivityCenter());
		project.setStation(projectCopy.getStation());
		project.setForumId(projectCopy.getForumId());
		
		return project;
	}
}
