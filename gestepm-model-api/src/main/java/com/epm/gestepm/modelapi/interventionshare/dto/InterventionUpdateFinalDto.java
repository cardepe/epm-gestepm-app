package com.epm.gestepm.modelapi.interventionshare.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class InterventionUpdateFinalDto {

    private Long id;

    private String description;

    private Integer equipmentHours;

    private List<MultipartFile> files;

    private MultipartFile materialsFile;

    private List<InterventionMaterialUpdateDto> materials;

    private String signature;

    private String signatureOp;

    private String clientName;

    private Boolean clientNotif;

}
