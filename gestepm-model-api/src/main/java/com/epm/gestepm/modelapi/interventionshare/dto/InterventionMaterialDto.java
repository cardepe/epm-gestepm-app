package com.epm.gestepm.modelapi.interventionshare.dto;

import lombok.Data;

@Data
public class InterventionMaterialDto {

    private Long id;

    private Long interventionId;

    private Integer units;

    private String description;

    private String reference;

}
