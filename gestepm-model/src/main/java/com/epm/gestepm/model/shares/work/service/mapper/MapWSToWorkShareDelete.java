package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.model.shares.work.dao.entity.deleter.WorkShareDelete;
import com.epm.gestepm.modelapi.shares.work.dto.deleter.WorkShareDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSToWorkShareDelete {

  WorkShareDelete from(WorkShareDeleteDto deleteDto);

}
