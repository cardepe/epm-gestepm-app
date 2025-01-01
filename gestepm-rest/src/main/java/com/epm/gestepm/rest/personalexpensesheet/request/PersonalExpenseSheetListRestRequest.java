package com.epm.gestepm.rest.personalexpensesheet.request;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetStatusEnumDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonalExpenseSheetListRestRequest extends RestRequest {

    private List<Integer> ids;

    private Integer projectId;

    private Integer userId;

    private String description;

    private LocalDateTime startDate;

    private String status;

    private String observations;

}
