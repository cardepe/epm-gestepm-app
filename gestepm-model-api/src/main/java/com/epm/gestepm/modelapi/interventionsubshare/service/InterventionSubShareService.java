package com.epm.gestepm.modelapi.interventionsubshare.service;

import com.epm.gestepm.modelapi.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public interface InterventionSubShareService {
	
	InterventionSubShare getById(Long id);
	InterventionSubShare getByShareAndOrder(Long shareId, Long interventionId);
	InterventionSubShare save(InterventionSubShare interventionShare);
	InterventionSubShare getOpenIntervention(Long shareId);
	byte[] generateInterventionSharePdf(InterventionSubShare subShare, Locale locale);
	byte[] generateInterventionShareMaterialsPdf(InterventionSubShare subShare, Locale locale);
	List<InterventionSubShare> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	List<InterventionSubShare> getWeekSigningsByProjectId(Date startDate, Date endDate, Long projectId);
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	List<ShareTableDTO> getShareTableByUserSigningId(Long userSigning);

	List<PdfFileDTO> generateSharesByProjectAndInterval(Long projectId, Date startDate, Date endDate);
}
