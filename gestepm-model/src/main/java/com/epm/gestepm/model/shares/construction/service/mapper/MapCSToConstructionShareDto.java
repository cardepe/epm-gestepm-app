package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShare;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapCSToConstructionShareDto {

    ConstructionShareDto from(ConstructionShare entity);

    List<ConstructionShareDto> from(List<ConstructionShare> list);

    default Page<ConstructionShareDto> from(Page<ConstructionShare> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }

}
