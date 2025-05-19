package com.epm.gestepm.rest.shares.construction.request;

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
public class ConstructionShareBreakListRestRequest extends RestRequest {

    private Integer id;

    private List<Integer> ids;

    private List<Integer> constructionShareIds;

    private String status;

}
