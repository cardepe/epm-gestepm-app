package com.epm.gestepm.modelapi.inspection.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MaterialDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer inspectionId;

    @NotNull
    private String description;

    @NotNull
    private Integer units;

    @NotNull
    private String reference;

}