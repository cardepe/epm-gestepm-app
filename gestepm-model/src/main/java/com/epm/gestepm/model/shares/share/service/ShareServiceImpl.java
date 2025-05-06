package com.epm.gestepm.model.shares.share.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.share.dao.ShareDao;
import com.epm.gestepm.model.shares.share.dao.entity.Share;
import com.epm.gestepm.model.shares.share.dao.entity.filter.ShareFilter;
import com.epm.gestepm.model.shares.share.service.mapper.*;
import com.epm.gestepm.modelapi.shares.share.dto.ShareDto;
import com.epm.gestepm.modelapi.shares.share.dto.filter.ShareFilterDto;
import com.epm.gestepm.modelapi.shares.share.service.ShareService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_READ_CS;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service
@AllArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class ShareServiceImpl implements ShareService {

    private final ShareDao shareDao;

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "List shares")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing shares",
            msgOut = "Listing shares OK",
            errorMsg = "Failed to list shares")
    public List<ShareDto> list(ShareFilterDto filterDto) {
        final ShareFilter filter = getMapper(MapSToShareFilter.class).from(filterDto);

        final List<Share> list = this.shareDao.list(filter);

        return getMapper(MapSToShareDto.class).from(list);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing shares",
            msgOut = "Listing shares OK",
            errorMsg = "Failed to list shares")
    public Page<ShareDto> list(ShareFilterDto filterDto, Long offset, Long limit) {

        final ShareFilter filter = getMapper(MapSToShareFilter.class).from(filterDto);

        final Page<Share> page = this.shareDao.list(filter, offset, limit);

        return getMapper(MapSToShareDto.class).from(page);
    }
}
