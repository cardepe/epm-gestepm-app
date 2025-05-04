package com.epm.gestepm.model.inspection.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class Material implements Serializable {

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
