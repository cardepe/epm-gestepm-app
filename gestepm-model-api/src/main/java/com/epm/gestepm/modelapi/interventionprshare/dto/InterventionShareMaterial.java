package com.epm.gestepm.modelapi.interventionprshare.dto;

import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "intervention_share_materials")
public class InterventionShareMaterial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "INTERVENTION_ID", nullable = false)
	private InterventionSubShare interventionSubShare;

	@Column(name = "UNITS", nullable = false, length = 11)
	private Integer units;
	
	@Column(name = "DESCRIPTION", length = 256)
	private String description;
	
	@Column(name = "REFERENCE", nullable = false, length = 11)
	private String reference;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InterventionSubShare getInterventionSubShare() {
		return interventionSubShare;
	}

	public void setInterventionSubShare(InterventionSubShare interventionSubShare) {
		this.interventionSubShare = interventionSubShare;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
