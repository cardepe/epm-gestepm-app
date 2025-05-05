package com.epm.gestepm.rest.shares.noprogrammed.request;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.modelapi.shares.common.dto.ShareStatusDto;
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
public class NoProgrammedShareListRestRequest extends RestRequest {

    private List<Integer> ids;

    private List<Integer> userIds;

    private List<Integer> projectIds;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String status;

}
