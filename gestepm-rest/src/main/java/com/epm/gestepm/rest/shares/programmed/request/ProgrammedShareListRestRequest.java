package com.epm.gestepm.rest.shares.programmed.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProgrammedShareListRestRequest extends RestRequest {

    private List<Integer> ids;

    private List<Integer> userIds;

    private List<Integer> projectIds;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String status;

}
