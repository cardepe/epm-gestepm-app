package com.epm.gestepm.rest.shares.programmed.request;

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
public class ProgrammedShareBreakListRestRequest extends RestRequest {

    private Integer programmedShareId;

    private List<Integer> ids;

    private String status;

}
