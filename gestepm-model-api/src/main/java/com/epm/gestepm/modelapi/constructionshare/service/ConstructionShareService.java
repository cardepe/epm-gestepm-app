package com.epm.gestepm.modelapi.constructionshare.service;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public interface ConstructionShareService {
	
	ConstructionShare getConstructionShareById(Long id);
	ConstructionShare save(ConstructionShare constructionShare);
	ConstructionShare create(ConstructionShare constructionShare, List<MultipartFile> files);
	List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> getShareTableByUserId(Long userId, Long projectId, Integer progress);
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	List<ShareTableDTO> getShareTableByUserSigningId(Long userSigning);
	void deleteById(Long id);
	List<ConstructionShare> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	List<ConstructionShare> getWeekSigningsByProjectId(OffsetDateTime startDate, OffsetDateTime endDate, Long projectId);
	List<DailyPersonalSigningDTO> getDailyConstructionShareDTOByUserIdAndDate(Long userId, int month, int year);
	byte[] generateConstructionSharePdf(ConstructionShare share, Locale locale);

	List<PdfFileDTO> generateSharesByProjectAndInterval(Long projectId, OffsetDateTime startDate, OffsetDateTime endDate);
}
