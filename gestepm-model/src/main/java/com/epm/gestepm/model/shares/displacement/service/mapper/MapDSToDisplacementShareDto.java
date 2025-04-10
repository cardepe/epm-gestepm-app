package com.epm.gestepm.model.shares.displacement.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.displacement.dao.entity.DisplacementShare;
import com.epm.gestepm.modelapi.shares.displacement.dto.DisplacementShareDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapDSToDisplacementShareDto {

    DisplacementShareDto from(DisplacementShare entity);

    List<DisplacementShareDto> from(List<DisplacementShare> list);

    default Page<DisplacementShareDto> from(Page<DisplacementShare> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }

}
