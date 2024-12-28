package com.epm.gestepm.modelapi.personalsigning.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PersonalSigningResumeDTO {

    private String projectName;

    private String type;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

}
