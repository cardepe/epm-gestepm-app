package com.epm.gestepm.modelapi.displacement.dto;

import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.project.dto.Project;
import lombok.Data;

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

@Data
@Entity
@Table(name = "displacement")
public class Displacement {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="displacement_id", unique=true, nullable=false, precision=10)
    private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "activity_center_id", nullable = false)
	private ActivityCenter activityCenter;
	
    @Column(name="name", nullable=false, length=128)
    private String title;
    
    @Column(name="type", nullable=false)
    private String displacementType;
    
    @Column(name="total_time", nullable=false, length=5)
    private int totalTime;
    
    @OneToMany(mappedBy = "displacement", fetch = FetchType.LAZY)
	private List<DisplacementShare> displacementShare;
    
    @ManyToMany
	@JoinTable(name = "project_displacements", joinColumns = @JoinColumn(name = "DISPLACEMENT_ID"), inverseJoinColumns = @JoinColumn(name = "PROJECT_ID"))
	private List<Project> projects;

}
