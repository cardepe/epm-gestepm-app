package com.epm.gestepm.rest.inspection.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InspectionBreakFindRestRequest extends RestRequest {

    @NotNull
    private Integer shareId;

    @NotNull
    private Integer inspectionId;

    @NotNull
    private Integer id;

}
