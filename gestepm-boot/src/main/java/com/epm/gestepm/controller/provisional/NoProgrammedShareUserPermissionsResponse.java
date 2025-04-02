package com.epm.gestepm.controller.provisional;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoProgrammedShareUserPermissionsResponse {

    private Boolean hasRole;

    private Boolean canClose;

}
