package com.epm.gestepm.modelapi.shares.share.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ShareDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull
    private String type;

    @NotNull
    private String detailUrl;

}
