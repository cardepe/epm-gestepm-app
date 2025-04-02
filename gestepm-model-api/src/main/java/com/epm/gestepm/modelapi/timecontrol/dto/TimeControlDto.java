package com.epm.gestepm.modelapi.timecontrol.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TimeControlDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private TimeControlTypeEnumDto type;

    private String description;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

}
