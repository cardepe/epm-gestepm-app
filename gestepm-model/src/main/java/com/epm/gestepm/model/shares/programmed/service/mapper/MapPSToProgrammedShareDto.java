package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShare;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapPSToProgrammedShareDto {

    ProgrammedShareDto from(ProgrammedShare entity);

    List<ProgrammedShareDto> from(List<ProgrammedShare> list);

    default Page<ProgrammedShareDto> from(Page<ProgrammedShare> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }

}
