package com.epm.gestepm.modelapi.inspection.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MaterialCreateDto implements Serializable {

    private Integer inspectionId;

    @NotNull
    private String description;

    @NotNull
    private Integer units;

    @NotNull
    private String reference;

}
