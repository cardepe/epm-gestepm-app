package com.epm.gestepm.rest.holiday.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HolidayListRestRequest extends RestRequest {

    private List<Integer> ids;

    private String name;

    private Integer day;

    private Integer month;

    final List<Integer> countryIds;

    final List<Integer> activityCenterIds;

}
