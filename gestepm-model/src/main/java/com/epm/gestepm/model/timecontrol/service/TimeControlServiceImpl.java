package com.epm.gestepm.model.timecontrol.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.model.timecontrol.dao.TimeControlDao;
import com.epm.gestepm.model.timecontrol.dao.entity.TimeControl;
import com.epm.gestepm.model.timecontrol.dao.entity.filter.TimeControlFilter;
import com.epm.gestepm.model.timecontrol.service.mapper.*;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDto;
import com.epm.gestepm.modelapi.timecontrol.dto.filter.TimeControlFilterDto;
import com.epm.gestepm.modelapi.timecontrol.service.TimeControlService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.timecontrol.security.TimeControlPermission.PRMT_READ_TC;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@AllArgsConstructor
@Service("timeControlService")
@EnableExecutionLog(layerMarker = SERVICE)
public class TimeControlServiceImpl implements TimeControlService {

    private final TimeControlDao timeControlDao;

    @Override
    @RequirePermits(value = PRMT_READ_TC, action = "List time controls")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing time controls",
            msgOut = "Listing time controls OK",
            errorMsg = "Failed to list time controls")
    public List<TimeControlDto> list(TimeControlFilterDto filterDto) {

        final TimeControlFilter filter = getMapper(MapTCToTimeControlFilter.class).from(filterDto);

        final List<TimeControl> list = this.timeControlDao.list(filter);

        return getMapper(MapTCToTimeControlDto.class).from(list);
    }
}
