package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.work.dao.entity.WorkShare;
import com.epm.gestepm.modelapi.shares.work.dto.WorkShareDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapWSToWorkShareDto {

    WorkShareDto from(WorkShare entity);

    List<WorkShareDto> from(List<WorkShare> list);

    default Page<WorkShareDto> from(Page<WorkShare> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }

}
