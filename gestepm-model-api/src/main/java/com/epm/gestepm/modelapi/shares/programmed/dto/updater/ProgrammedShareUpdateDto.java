package com.epm.gestepm.modelapi.shares.programmed.dto.updater;

import com.epm.gestepm.modelapi.shares.programmed.dto.creator.ProgrammedShareFileCreateDto;
import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ProgrammedShareUpdateDto {

    @NotNull
    private Integer id;

    private Integer projectId;

    private Integer secondTechnicalId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String observations;

    private String customerSignature;

    private String operatorSignature;

    @Singular
    private Set<ProgrammedShareFileCreateDto> files;

    private Boolean notify;

}
