package com.epm.gestepm.model.inspection.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class InspectionFile implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer inspectionId;

    @NotNull
    private String name;

    @NotNull
    private String content;

}
