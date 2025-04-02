package com.epm.gestepm.rest.timecontrol.request;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlTypeEnumDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TimeControlListRestRequest extends RestRequest {

    private Integer userId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<String> types;

}
