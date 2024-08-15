package com.epm.gestepm.rest.common;

import com.epm.gestepm.lib.controller.response.ResSuccess;
import org.mapstruct.Mapper;

@Mapper
public interface ResSuccessMapper {

    com.epm.gestepm.restapi.openapi.model.ResSuccess from(ResSuccess success);

}
