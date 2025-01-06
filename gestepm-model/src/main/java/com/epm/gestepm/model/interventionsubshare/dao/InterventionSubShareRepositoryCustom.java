package com.epm.gestepm.model.interventionsubshare.dao;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;

import java.util.List;

public interface InterventionSubShareRepositoryCustom {
	List<ShareTableDTO> findShareTableByProjectId(Long projectId);
	List<ShareTableDTO> findShareTableByUserSigningId(Long userSigningId);
}
