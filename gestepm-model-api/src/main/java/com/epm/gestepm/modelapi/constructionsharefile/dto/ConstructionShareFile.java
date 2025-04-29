package com.epm.gestepm.modelapi.constructionsharefile.dto;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "construction_share_file")
public class ConstructionShareFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "construction_share_file_id", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "content", nullable = false, length = 64)
	private String name;

	@Column(name = "CONTENT", nullable = false)
	private byte[] content;

	@ManyToOne(optional = false)
	@JoinColumn(name = "construction_share_id", nullable = false)
	private ConstructionShare constructionShare;

}
