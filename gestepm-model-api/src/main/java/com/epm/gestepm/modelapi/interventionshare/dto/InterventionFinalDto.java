package com.epm.gestepm.modelapi.interventionshare.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class InterventionFinalDto {

    private Long id;

    private Long userSigningId;

    private Integer orderId;

    private Long interventionId;

    private Integer action;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private String description;

    private Long firstTechnicalId;

    private Long secondTechnicalId;

    private String signature;

    private String signatureOp;

    private String clientName;

    private String materials;

    private String mrSignature;

    private byte[] materialsFile;

    private String materialsFileExt;

    private Long topicId;

    private Long displacementShareId;

    private Integer equipmentHours;

    private List<InterventionMaterialDto> interventionShareMaterials;

}
