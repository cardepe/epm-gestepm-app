package com.epm.gestepm.modelapi.interventionsubshare.service;

import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;

import java.util.List;

public interface InterventionSubShareService {
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	List<ShareTableDTO> getShareTableByUserSigningId(Long userSigning);
}
