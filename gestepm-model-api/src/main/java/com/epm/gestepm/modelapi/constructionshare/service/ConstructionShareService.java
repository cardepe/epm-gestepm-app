package com.epm.gestepm.modelapi.constructionshare.service;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public interface ConstructionShareService {
	
	ConstructionShare getConstructionShareById(Long id);
	ConstructionShare save(ConstructionShare constructionShare);
	ConstructionShare create(ConstructionShare constructionShare, List<MultipartFile> files);
	List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	List<ShareTableDTO> getShareTableByUserSigningId(Long userSigning);
	void deleteById(Long id);
	List<ConstructionShare> getWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
	List<ConstructionShare> getWeekSigningsByProjectId(LocalDateTime startDate, LocalDateTime endDate, Long projectId);
	byte[] generateConstructionSharePdf(ConstructionShare share, Locale locale);

	List<PdfFileDTO> generateSharesByProjectAndInterval(Long projectId, LocalDateTime startDate, LocalDateTime endDate);
}
