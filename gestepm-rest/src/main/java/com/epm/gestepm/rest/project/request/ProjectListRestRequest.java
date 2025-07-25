package com.epm.gestepm.rest.project.request;

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
public class ProjectListRestRequest extends RestRequest {

    private List<Integer> ids;

    private String nameContains;

    private List<String> types;

    private List<Integer> activityCenterIds;

    private Integer state;

    private List<Integer> responsibleIds;

    private List<Integer> memberIds;

    private Boolean role;

}
