package com.epm.gestepm.modelapi.interventionprshare.service;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;


public interface InterventionPrShareService {
	
	InterventionPrShare getInterventionPrShareById(Long id);
	InterventionPrShare save(InterventionPrShare interventionPrShare);
	InterventionPrShare create(InterventionPrShare interventionPrShare, List<MultipartFile> files);
	List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	List<ShareTableDTO> getShareTableByUserSigningId(Long userSigning);
	void deleteById(Long id);
	List<InterventionPrShare> getWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
	List<InterventionPrShare> getWeekSigningsByProjectId(LocalDateTime startDate, LocalDateTime endDate, Long projectId);
	byte[] generateInterventionSharePdf(InterventionPrShare share, Locale locale);

	List<PdfFileDTO> generateSharesByProjectAndInterval(Long projectId, LocalDateTime startDate, LocalDateTime endDate);
}
