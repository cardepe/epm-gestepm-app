package com.epm.gestepm.model.shares.share.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Share implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer userId;

    private Integer projectId;

    private String projectName;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull
    private ShareTypeEnum type;

    private String detailUrl;

}
