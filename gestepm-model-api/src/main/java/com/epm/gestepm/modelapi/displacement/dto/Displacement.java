package com.epm.gestepm.modelapi.displacement.dto;

import com.epm.gestepm.modelapi.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.project.dto.Project;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "displacements")
public class Displacement {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID", unique=true, nullable=false, precision=10)
    private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ACTIVITY_CENTER", nullable = false)
	private ActivityCenter activityCenter;
	
    @Column(name="TITLE", nullable=false, length=128)
    private String title;
    
    @Column(name="DISPLACEMENT_TYPE", nullable=false, length=1)
    private int displacementType;
    
    @Column(name="TOTAL_TIME", nullable=false, length=5)
    private int totalTime;
    
    @OneToMany(mappedBy = "displacement", fetch = FetchType.LAZY)
	private List<DisplacementShare> displacementShare;
    
    @ManyToMany
	@JoinTable(name = "project_displacements", joinColumns = @JoinColumn(name = "DISPLACEMENT_ID"), inverseJoinColumns = @JoinColumn(name = "PROJECT_ID"))
	private List<Project> projects;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ActivityCenter getActivityCenter() {
		return activityCenter;
	}

	public void setActivityCenter(ActivityCenter activityCenter) {
		this.activityCenter = activityCenter;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDisplacementType() {
		return displacementType;
	}

	public void setDisplacementType(int displacementType) {
		this.displacementType = displacementType;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public List<DisplacementShare> getDisplacementShare() {
		return displacementShare;
	}

	public void setDisplacementShare(List<DisplacementShare> displacementShare) {
		this.displacementShare = displacementShare;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
}
