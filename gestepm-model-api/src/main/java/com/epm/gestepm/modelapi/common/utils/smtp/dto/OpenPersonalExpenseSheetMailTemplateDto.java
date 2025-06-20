package com.epm.gestepm.modelapi.common.utils.smtp.dto;

import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetDto;
import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@Data
public class OpenPersonalExpenseSheetMailTemplateDto {

    @NotNull
    private Locale locale;

    @NotNull
    private String email;

    @NotNull
    private PersonalExpenseSheetDto personalExpenseSheetDto;

    @NotNull
    private User user;

    @NotNull
    private Project project;

    @NotNull
    private String template;

    @NotNull
    private String subject;

}
