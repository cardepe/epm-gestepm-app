package com.epm.gestepm.modelapi.workshare.service;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import com.epm.gestepm.modelapi.workshare.dto.WorkShareTableDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public interface WorkShareService {
	
	WorkShare getWorkShareById(Long id);
	WorkShare save(WorkShare workShare);
	WorkShare create(WorkShare workShare, List<MultipartFile> files);
	void deleteById(Long id);
	List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	List<ShareTableDTO> getShareTableByUserSigningId(Long userSigning);
	Long getWorkSharesCountByUser(Long userId);
	List<WorkShareTableDTO> getWorkSharesByUserDataTables(Long userId, PaginationCriteria pagination);
	List<WorkShare> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	List<WorkShare> getWeekSigningsByProjectId(LocalDateTime startDate, LocalDateTime endDate, Long projectId);
	byte[] generateWorkSharePdf(WorkShare share, Locale locale);

	List<PdfFileDTO> generateSharesByProjectAndInterval(Long projectId, LocalDateTime startDate, LocalDateTime endDate);
}
