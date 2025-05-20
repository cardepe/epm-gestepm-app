package com.epm.gestepm.modelapi.shares.breaks.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ShareBreakCreateDto {

    private Integer constructionShareId;

    private Integer programmedShareId;

    private Integer inspectionId;

    private Integer workShareId;

    @NotNull
    private LocalDateTime startDate;

}
