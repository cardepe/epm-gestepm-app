package com.epm.gestepm.model.shares.breaks.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ShareBreak implements Serializable {

    @NotNull
    private Integer id;

    private Integer constructionShareId;

    private Integer programmedShareId;

    private Integer inspectionId;

    private Integer workShareId;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
