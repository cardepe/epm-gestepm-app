package com.epm.gestepm.modelapi.workshare.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import com.epm.gestepm.modelapi.workshare.dto.WorkShareTableDTO;
import org.springframework.web.multipart.MultipartFile;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

public interface WorkShareService {
	
	WorkShare getWorkShareById(Long id);
	WorkShare save(WorkShare workShare);
	WorkShare create(WorkShare workShare, List<MultipartFile> files);
	void deleteById(Long id);
	List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> getShareTableByUserId(Long userId, Long projectId, Integer progress);
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	List<ShareTableDTO> getShareTableByUserSigningId(Long userSigning);
	Long getWorkSharesCountByUser(Long userId);
	List<WorkShareTableDTO> getWorkSharesByUserDataTables(Long userId, PaginationCriteria pagination);
	List<WorkShare> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	List<WorkShare> getWeekSigningsByProjectId(Date startDate, Date endDate, Long projectId);
	List<ExpensesMonthDTO> getExpensesMonthDTOByProjectId(Long projectId, Integer year);
	byte[] generateWorkSharePdf(WorkShare share, Locale locale);

	List<PdfFileDTO> generateSharesByProjectAndInterval(Long projectId, Date startDate, Date endDate);
}
