package com.epm.gestepm.modelapi.timecontrol.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TimeControlWoffuDto {

    @NotNull
    private Integer id;

    @NotNull
    private TimeControlTypeEnumDto type;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private String reason;
}
