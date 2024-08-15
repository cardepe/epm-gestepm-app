package com.epm.gestepm.modelapi.interventionsubshare.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionShareMaterial;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionNoPrDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionUpdateFinalDto;
import com.epm.gestepm.modelapi.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShareTableDTO;
import com.epm.gestepm.modelapi.user.dto.User;

public interface InterventionSubShareService {
	
	InterventionSubShare getById(Long id);
	InterventionSubShare getByShareAndOrder(Long shareId, Long interventionId);
	List<InterventionShareMaterial> listInterventionShareMaterial(Long interventionId);
	InterventionSubShare update(InterventionUpdateFinalDto updateDto) throws Exception;
	InterventionSubShare save(InterventionSubShare interventionShare);
	void updateInterventionInfo(InterventionSubShare interventionShare, InterventionNoPrDTO interventionNoPrDTO);
	InterventionSubShare closeIntervention(InterventionSubShare interventionShare, InterventionNoPrDTO interventionNoPrDTO, User user, String userIp, Locale locale);
	void deleteById(Long id);
	Long getInterventionSubSharesCountByShareId(Long shareId);
	List<InterventionSubShareTableDTO> getInterventionSubSharesByShareDataTables(Long shareId, PaginationCriteria pagination);
	InterventionSubShare getOpenIntervention(Long shareId);
	byte[] generateInterventionSharePdf(InterventionSubShare subShare, Locale locale);
	byte[] generateInterventionShareMaterialsPdf(InterventionSubShare subShare, Locale locale);
	List<InterventionSubShare> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	List<InterventionSubShare> getWeekSigningsByProjectId(Date startDate, Date endDate, Long projectId);
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	List<ShareTableDTO> getShareTableByUserSigningId(Long userSigning);

	List<PdfFileDTO> generateSharesByProjectAndInterval(Long projectId, Date startDate, Date endDate);
}
