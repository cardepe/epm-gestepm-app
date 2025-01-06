package com.epm.gestepm.modelapi.common.utils.smtp.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class EmailTemplateDto {

    @NotNull
    private String from;

    @NotNull
    private String to;

    @NotNull
    private String subject;

    @NotNull
    private Map<String, String> params;

}
