package com.epm.gestepm.modelapi.shares;

import com.epm.gestepm.modelapi.interventionshare.dto.PdfFileDTO;

import java.time.OffsetDateTime;
import java.util.List;

public interface ShareDecorator {

    List<PdfFileDTO> exportShares(Integer projectId, OffsetDateTime startDate, OffsetDateTime endDate);

}
