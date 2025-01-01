package com.epm.gestepm.modelapi.deprecated.interventionsubshare.service;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;

import java.util.List;

public interface InterventionSubShareService {
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	List<ShareTableDTO> getShareTableByUserSigningId(Long userSigning);
}
