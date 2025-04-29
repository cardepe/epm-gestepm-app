package com.epm.gestepm.modelapi.constructionshare.service;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public interface ConstructionShareOldService {
	
	ConstructionShare getConstructionShareById(Long id);
	ConstructionShare create(ConstructionShare constructionShare, List<MultipartFile> files);
	List<ConstructionShare> getWeekSigningsByProjectId(LocalDateTime startDate, LocalDateTime endDate, Long projectId);
	byte[] generateConstructionSharePdf(ConstructionShare share, Locale locale);

	List<PdfFileDTO> generateSharesByProjectAndInterval(Long projectId, LocalDateTime startDate, LocalDateTime endDate);
}
