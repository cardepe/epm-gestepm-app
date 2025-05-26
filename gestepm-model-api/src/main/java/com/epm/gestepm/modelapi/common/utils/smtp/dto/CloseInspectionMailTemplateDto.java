package com.epm.gestepm.modelapi.common.utils.smtp.dto;

import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.userold.dto.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@Data
public class CloseInspectionMailTemplateDto {

    @NotNull
    private Locale locale;

    @NotNull
    private String email;

    @NotNull
    private NoProgrammedShareDto noProgrammedShare;

    @NotNull
    private InspectionDto inspection;

    @NotNull
    private User user;

    @NotNull
    private Project project;

    @NotNull
    private byte[] pdf;

}
