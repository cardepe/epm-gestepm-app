package com.epm.gestepm.modelapi.interventionsubsharefile.dto;

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
@Table(name = "intervention_sub_share_files")
public class InterventionSubShareFile {

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
	@JoinColumn(name = "SUB_SHARE_ID", nullable = false)
	private InterventionSubShare interventionSubShare;

	public InterventionSubShareFile() {
		super();
	}

	public InterventionSubShareFile(Long id, String name, String ext, byte[] content,
			InterventionSubShare interventionSubShare) {
		super();
		this.id = id;
		this.name = name;
		this.ext = ext;
		this.content = content;
		this.interventionSubShare = interventionSubShare;
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

	public InterventionSubShare getInterventionSubShare() {
		return interventionSubShare;
	}

	public void setInterventionSubShare(InterventionSubShare interventionSubShare) {
		this.interventionSubShare = interventionSubShare;
	}

}
