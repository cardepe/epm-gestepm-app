package com.epm.gestepm.rest.shares.work.request;

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
public class WorkShareBreakListRestRequest extends RestRequest {

    private Integer workShareId;

    private List<Integer> ids;

    private String status;

}
