package com.epm.gestepm.modelapi.shares.breaks.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ShareBreakUpdateDto {

    @NotNull
    private Integer id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
