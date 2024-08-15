package com.epm.gestepm.modelapi.interventionshare.dto;

import lombok.Data;

@Data
public class InterventionMaterialUpdateDto {

    private Long id;

    private Integer units;

    private String description;

    private String reference;

}
