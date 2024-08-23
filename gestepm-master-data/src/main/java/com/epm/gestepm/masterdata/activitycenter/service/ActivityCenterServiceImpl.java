package com.epm.gestepm.masterdata.activitycenter.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.activitycenter.dao.ActivityCenterDao;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.ActivityCenter;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.creator.ActivityCenterCreate;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.deleter.ActivityCenterDelete;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.filter.ActivityCenterFilter;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.finder.ActivityCenterByIdFinder;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.updater.ActivityCenterUpdate;
import com.epm.gestepm.masterdata.activitycenter.service.mapper.*;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.creator.ActivityCenterCreateDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.deleter.ActivityCenterDeleteDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.filter.ActivityCenterFilterDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.finder.ActivityCenterByIdFinderDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.updater.ActivityCenterUpdateDto;
import com.epm.gestepm.masterdata.api.activitycenter.exception.ActivityCenterNotFoundException;
import com.epm.gestepm.masterdata.api.activitycenter.service.ActivityCenterService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.masterdata.api.activitycenter.security.ActivityCenterPermission.PRMT_EDIT_AC;
import static com.epm.gestepm.masterdata.api.activitycenter.security.ActivityCenterPermission.PRMT_READ_AC;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service("activityCenterService")
@EnableExecutionLog(layerMarker = SERVICE)
public class ActivityCenterServiceImpl implements ActivityCenterService {

  private final ActivityCenterDao activityCenterDao;

  public ActivityCenterServiceImpl(ActivityCenterDao activityCenterDao) {
    this.activityCenterDao = activityCenterDao;
  }

  @Override
  @RequirePermits(value = PRMT_READ_AC, action = "List activity centers")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Listing activity centers",
          msgOut = "Listing activity centers OK",
          errorMsg = "Failed to list activity centers")
  public List<ActivityCenterDto> list(ActivityCenterFilterDto filterDto) {

    final ActivityCenterFilter filter = getMapper(MapACToActivityCenterFilter.class).from(filterDto);

    final List<ActivityCenter> list = this.activityCenterDao.list(filter);
    list.sort(Comparator.comparing(ActivityCenter::getName));

    return getMapper(MapACToActivityCenterDto.class).from(list);
  }

  @Override
  @RequirePermits(value = PRMT_READ_AC, action = "Page activity centers")
  @LogExecution(operation = OP_READ,
      debugOut = true,
      msgIn = "Paginating activity centers",
      msgOut = "Paginating activity centers OK",
      errorMsg = "Failed to paginate activity centers")
  public Page<ActivityCenterDto> list(ActivityCenterFilterDto filterDto, Long offset, Long limit) {

    final ActivityCenterFilter filter = getMapper(MapACToActivityCenterFilter.class).from(filterDto);

    final Page<ActivityCenter> page = this.activityCenterDao.list(filter, offset, limit);

    return getMapper(MapACToActivityCenterDto.class).from(page);
  }

  @Override
  @RequirePermits(value = PRMT_READ_AC, action = "Find activity center by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding activity center by ID, result can be empty",
          msgOut = "Found activity center by ID",
          errorMsg = "Failed to find activity center by ID")
  public Optional<ActivityCenterDto> find(ActivityCenterByIdFinderDto finderDto) {

    final ActivityCenterByIdFinder finder = getMapper(MapACToActivityCenterByIdFinder.class).from(finderDto);

    final Optional<ActivityCenter> found = this.activityCenterDao.find(finder);

    return found.map(getMapper(MapACToActivityCenterDto.class)::from);
  }

  @Override
  @RequirePermits(value = PRMT_READ_AC, action = "Find activity center by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding activity center by ID, result is expected or will fail",
          msgOut = "Found activity center by ID",
          errorMsg = "Activity center by ID not found")
  public ActivityCenterDto findOrNotFound(ActivityCenterByIdFinderDto finderDto) {

    final Supplier<RuntimeException> notFound = () -> new ActivityCenterNotFoundException(finderDto.getId());

    return this.find(finderDto).orElseThrow(notFound);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_AC, action = "Create new activity center")
  @LogExecution(operation = OP_CREATE,
          debugOut = true,
          msgIn = "Creating new activity center",
          msgOut = "New activity center created OK",
          errorMsg = "Failed to create new activity center")
  public ActivityCenterDto create(ActivityCenterCreateDto createDto) {

    final ActivityCenterCreate create = getMapper(MapACToActivityCenterCreate.class).from(createDto);

    final ActivityCenter result = this.activityCenterDao.create(create);

    return getMapper(MapACToActivityCenterDto.class).from(result);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_AC, action = "Update activity center")
  @LogExecution(operation = OP_UPDATE,
          debugOut = true,
          msgIn = "Updating activity center",
          msgOut = "Activity center updated OK",
          errorMsg = "Failed to update activity center")
  public ActivityCenterDto update(ActivityCenterUpdateDto updateDto) {

    final ActivityCenterByIdFinderDto finderDto = new ActivityCenterByIdFinderDto();
    finderDto.setId(updateDto.getId());

    findOrNotFound(finderDto);

    final ActivityCenterUpdate update = getMapper(MapACToActivityCenterUpdate.class).from(updateDto);

    final ActivityCenter updated = this.activityCenterDao.update(update);

    return getMapper(MapACToActivityCenterDto.class).from(updated);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_AC, action = "Delete activity center")
  @LogExecution(operation = OP_DELETE,
          debugOut = true,
          msgIn = "Deleting activity center",
          msgOut = "Activity center deleted OK",
          errorMsg = "Failed to delete activity center")
  public void delete(ActivityCenterDeleteDto deleteDto) {

    final ActivityCenterByIdFinderDto finderDto = new ActivityCenterByIdFinderDto();
    finderDto.setId(deleteDto.getId());

    findOrNotFound(finderDto);

    final ActivityCenterDelete delete = getMapper(MapACToActivityCenterDelete.class).from(deleteDto);

    this.activityCenterDao.delete(delete);
  }
}
