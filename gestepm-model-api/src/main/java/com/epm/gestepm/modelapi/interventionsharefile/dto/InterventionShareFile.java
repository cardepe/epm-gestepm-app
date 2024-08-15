package com.epm.gestepm.modelapi.interventionsharefile.dto;

import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "intervention_share_files")
public class InterventionShareFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "NAME", nullable = false, length = 64)
	private String name;

	@Column(name = "EXT", nullable = false, length = 6)
	private String ext;

	@Column(name = "CONTENT", nullable = false)
	private byte[] content;

	@ManyToOne(optional = false)
	@JoinColumn(name = "SHARE_ID", nullable = false)
	private InterventionShare interventionShare;

	public InterventionShareFile() {
		super();
	}

	public InterventionShareFile(Long id, String name, String ext, byte[] content,
			InterventionShare interventionShare) {
		super();
		this.id = id;
		this.name = name;
		this.ext = ext;
		this.content = content;
		this.interventionShare = interventionShare;
	}

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

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public InterventionShare getInterventionShare() {
		return interventionShare;
	}

	public void setInterventionShare(InterventionShare interventionShare) {
		this.interventionShare = interventionShare;
	}

}
