package com.epm.gestepm.rest.displacement.request;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.restapi.openapi.model.DisplacementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DisplacementListRestRequest extends RestRequest {

    private List<Integer> ids;

    final List<Integer> activityCenterIds;

    private String name;

    private DisplacementType type;

}
