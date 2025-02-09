package com.epm.gestepm.modelapi.common.utils.smtp.dto;

import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.modelapi.user.dto.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@Data
public class OpenNoProgrammedShareMailTemplateDto {

    @NotNull
    private Locale locale;

    @NotNull
    private String email;

    @NotNull
    private NoProgrammedShareDto noProgrammedShare;

    @NotNull
    private User user;

    @NotNull
    private Project project;

}
