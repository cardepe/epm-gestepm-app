package com.epm.gestepm.modelapi.constructionshare.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class ConstructionShareDto {

    @NotNull
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    private Integer userSigningId;

    @NotNull
    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private String observations;

    private String signatureOp;

    private String materials;

    private String mrSignature;

    private Integer displacementShareId;

    private List<ConstructionShareFileDto> files;

}
