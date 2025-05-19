package com.epm.gestepm.modelapi.shares.breaks.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ShareBreakDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer shareId;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
