package com.epm.gestepm.rest.personalexpense.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonalExpenseListRestRequest extends RestRequest {

    private List<Integer> ids;

    private Integer personalExpenseSheetId;

}
