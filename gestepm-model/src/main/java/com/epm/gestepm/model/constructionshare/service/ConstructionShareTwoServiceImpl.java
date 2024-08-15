package com.epm.gestepm.model.constructionshare.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.constructionshare.dao.ConstructionShareDao;
import com.epm.gestepm.model.constructionshare.dao.entity.ConstructionShare;
import com.epm.gestepm.model.constructionshare.dao.entity.filter.ConstructionShareFilter;
import com.epm.gestepm.model.constructionshare.service.mapper.MapCSToConstructionShareDto;
import com.epm.gestepm.model.constructionshare.service.mapper.MapCSToConstructionShareFilter;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShareDto;
import com.epm.gestepm.modelapi.constructionshare.dto.filter.ConstructionShareFilterDto;
import com.epm.gestepm.modelapi.constructionshare.service.ConstructionShareService;
import com.epm.gestepm.modelapi.constructionshare.service.ConstructionShareTwoService;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import java.util.List;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service
@EnableExecutionLog(layerMarker = SERVICE)
public class ConstructionShareTwoServiceImpl implements ConstructionShareTwoService {

    private final ConstructionShareDao constructionShareDao;

    public ConstructionShareTwoServiceImpl(ConstructionShareDao constructionShareDao) {
        this.constructionShareDao = constructionShareDao;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing construction shares",
            msgOut = "Listing construction shares OK",
            errorMsg = "Failed to list construction shares")
    public Page<ConstructionShareDto> list(ConstructionShareFilterDto filterDto, Long offset, Long limit) {

        final ConstructionShareFilter filter = getMapper(MapCSToConstructionShareFilter.class).from(filterDto);

        final Page<ConstructionShare> page = this.constructionShareDao.list(filter, offset, limit);

        return getMapper(MapCSToConstructionShareDto.class).from(page);
    }
}
