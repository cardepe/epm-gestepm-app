package com.epm.gestepm.modelapi.shares;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.PdfFileDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ShareDecorator {

    List<PdfFileDTO> exportShares(Integer projectId, LocalDateTime startDate, LocalDateTime endDate);

}
