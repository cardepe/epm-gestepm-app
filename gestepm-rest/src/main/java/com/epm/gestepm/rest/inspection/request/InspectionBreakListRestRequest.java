package com.epm.gestepm.rest.inspection.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InspectionBreakListRestRequest extends RestRequest {

    private Integer shareId;

    private Integer inspectionId;

    private List<Integer> ids;

    private String status;

}
