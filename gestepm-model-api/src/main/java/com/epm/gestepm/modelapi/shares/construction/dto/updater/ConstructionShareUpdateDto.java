package com.epm.gestepm.modelapi.shares.construction.dto.updater;

import com.epm.gestepm.modelapi.shares.construction.dto.creator.ConstructionShareFileCreateDto;
import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ConstructionShareUpdateDto {

    @NotNull
    private Integer id;

    private Integer projectId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String observations;

    private String operatorSignature;

    @Singular
    private Set<ConstructionShareFileCreateDto> files;

    private Boolean notify;

}
