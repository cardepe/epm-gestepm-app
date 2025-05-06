package com.epm.gestepm.model.shares.share.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.share.dao.entity.Share;
import com.epm.gestepm.modelapi.shares.share.dto.ShareDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapSToShareDto {

    ShareDto from(Share entity);

    List<ShareDto> from(List<Share> list);

    default Page<ShareDto> from(Page<Share> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }

}
