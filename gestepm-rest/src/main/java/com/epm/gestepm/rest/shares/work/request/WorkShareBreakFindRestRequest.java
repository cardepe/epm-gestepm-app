package com.epm.gestepm.rest.shares.work.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WorkShareBreakFindRestRequest extends RestRequest {

    @NotNull
    private Integer workShareId;

    @NotNull
    private Integer id;

}
