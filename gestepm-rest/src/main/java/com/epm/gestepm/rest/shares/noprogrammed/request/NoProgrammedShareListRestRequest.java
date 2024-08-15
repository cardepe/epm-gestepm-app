package com.epm.gestepm.rest.shares.noprogrammed.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NoProgrammedShareListRestRequest extends RestRequest {

    private List<Integer> ids;

}
