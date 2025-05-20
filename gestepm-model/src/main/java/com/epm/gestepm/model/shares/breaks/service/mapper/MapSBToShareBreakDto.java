package com.epm.gestepm.model.shares.breaks.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.breaks.dao.entity.ShareBreak;
import com.epm.gestepm.modelapi.shares.breaks.dto.ShareBreakDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapSBToShareBreakDto {

    ShareBreakDto from(ShareBreak entity);

    List<ShareBreakDto> from(List<ShareBreak> list);

    default Page<ShareBreakDto> from(Page<ShareBreak> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }

}
