package com.epm.gestepm.modelapi.personalexpensesheet.dto.updater;

import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetStatusEnumDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class PersonalExpenseSheetUpdateDto {

    @NotNull
    private Integer id;

    private Integer projectId;

    private String description;

    private LocalDateTime startDate;

    private PersonalExpenseSheetStatusEnumDto status;

    private String observations;

}
