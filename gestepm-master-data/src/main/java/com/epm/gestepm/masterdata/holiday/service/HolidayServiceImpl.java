package com.epm.gestepm.masterdata.holiday.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.holiday.dto.HolidayDto;
import com.epm.gestepm.masterdata.api.holiday.dto.creator.HolidayCreateDto;
import com.epm.gestepm.masterdata.api.holiday.dto.deleter.HolidayDeleteDto;
import com.epm.gestepm.masterdata.api.holiday.dto.filter.HolidayFilterDto;
import com.epm.gestepm.masterdata.api.holiday.dto.finder.HolidayByIdFinderDto;
import com.epm.gestepm.masterdata.api.holiday.dto.updater.HolidayUpdateDto;
import com.epm.gestepm.masterdata.api.holiday.exception.HolidayNotFoundException;
import com.epm.gestepm.masterdata.api.holiday.service.HolidayService;
import com.epm.gestepm.masterdata.holiday.dao.HolidayDao;
import com.epm.gestepm.masterdata.holiday.dao.entity.Holiday;
import com.epm.gestepm.masterdata.holiday.dao.entity.creator.HolidayCreate;
import com.epm.gestepm.masterdata.holiday.dao.entity.deleter.HolidayDelete;
import com.epm.gestepm.masterdata.holiday.dao.entity.filter.HolidayFilter;
import com.epm.gestepm.masterdata.holiday.dao.entity.finder.HolidayByIdFinder;
import com.epm.gestepm.masterdata.holiday.dao.entity.updater.HolidayUpdate;
import com.epm.gestepm.masterdata.holiday.service.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.masterdata.api.holiday.security.HolidayPermission.PRMT_EDIT_H;
import static com.epm.gestepm.masterdata.api.holiday.security.HolidayPermission.PRMT_READ_H;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service("holidayService")
@EnableExecutionLog(layerMarker = SERVICE)
public class HolidayServiceImpl implements HolidayService {

  private final HolidayDao holidayDao;

  public HolidayServiceImpl(HolidayDao holidayDao) {
    this.holidayDao = holidayDao;
  }

  @Override
  @RequirePermits(value = PRMT_READ_H, action = "List holidays")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Listing holidays",
          msgOut = "Listing holidays OK",
          errorMsg = "Failed to list holidays")
  public List<HolidayDto> list(HolidayFilterDto filterDto) {

    final HolidayFilter filter = getMapper(MapHToHolidayFilter.class).from(filterDto);

    final List<Holiday> list = this.holidayDao.list(filter);

    return getMapper(MapHToHolidayDto.class).from(list);
  }

  @Override
  @RequirePermits(value = PRMT_READ_H, action = "Page holidays")
  @LogExecution(operation = OP_READ,
      debugOut = true,
      msgIn = "Paginating holidays",
      msgOut = "Paginating holidays OK",
      errorMsg = "Failed to paginate holidays")
  public Page<HolidayDto> list(HolidayFilterDto filterDto, Long offset, Long limit) {

    final HolidayFilter filter = getMapper(MapHToHolidayFilter.class).from(filterDto);

    final Page<Holiday> page = this.holidayDao.list(filter, offset, limit);

    return getMapper(MapHToHolidayDto.class).from(page);
  }

  @Override
  @RequirePermits(value = PRMT_READ_H, action = "Find holiday by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding holiday by ID, result can be empty",
          msgOut = "Found holiday by ID",
          errorMsg = "Failed to find holiday by ID")
  public Optional<HolidayDto> find(HolidayByIdFinderDto finderDto) {

    final HolidayByIdFinder finder = getMapper(MapHToHolidayByIdFinder.class).from(finderDto);

    final Optional<Holiday> found = this.holidayDao.find(finder);

    return found.map(getMapper(MapHToHolidayDto.class)::from);
  }

  @Override
  @RequirePermits(value = PRMT_READ_H, action = "Find holiday by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding holiday by ID, result is expected or will fail",
          msgOut = "Found holiday by ID",
          errorMsg = "Holiday by ID not found")
  public HolidayDto findOrNotFound(HolidayByIdFinderDto finderDto) {

    final Supplier<RuntimeException> notFound = () -> new HolidayNotFoundException(finderDto.getId());

    return this.find(finderDto).orElseThrow(notFound);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_H, action = "Create new holiday")
  @LogExecution(operation = OP_CREATE,
          debugOut = true,
          msgIn = "Creating new holiday",
          msgOut = "New holiday created OK",
          errorMsg = "Failed to create new holiday")
  public HolidayDto create(HolidayCreateDto createDto) {

    final HolidayCreate create = getMapper(MapHToHolidayCreate.class).from(createDto);

    final Holiday result = this.holidayDao.create(create);

    return getMapper(MapHToHolidayDto.class).from(result);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_H, action = "Update holiday")
  @LogExecution(operation = OP_UPDATE,
          debugOut = true,
          msgIn = "Updating holiday",
          msgOut = "Holiday updated OK",
          errorMsg = "Failed to update holiday")
  public HolidayDto update(HolidayUpdateDto updateDto) {

    final HolidayByIdFinderDto finderDto = new HolidayByIdFinderDto();
    finderDto.setId(updateDto.getId());

    findOrNotFound(finderDto);

    final HolidayUpdate update = getMapper(MapHToHolidayUpdate.class).from(updateDto);

    final Holiday updated = this.holidayDao.update(update);

    return getMapper(MapHToHolidayDto.class).from(updated);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_H, action = "Delete holiday")
  @LogExecution(operation = OP_DELETE,
          debugOut = true,
          msgIn = "Deleting holiday",
          msgOut = "Holiday deleted OK",
          errorMsg = "Failed to delete holiday")
  public void delete(HolidayDeleteDto deleteDto) {

    final HolidayByIdFinderDto finderDto = new HolidayByIdFinderDto();
    finderDto.setId(deleteDto.getId());

    findOrNotFound(finderDto);

    final HolidayDelete delete = getMapper(MapHToHolidayDelete.class).from(deleteDto);

    this.holidayDao.delete(delete);
  }
}
