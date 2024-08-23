package com.epm.gestepm.masterdata.displacement.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.displacement.dto.DisplacementDto;
import com.epm.gestepm.masterdata.api.displacement.dto.creator.DisplacementCreateDto;
import com.epm.gestepm.masterdata.api.displacement.dto.deleter.DisplacementDeleteDto;
import com.epm.gestepm.masterdata.api.displacement.dto.filter.DisplacementFilterDto;
import com.epm.gestepm.masterdata.api.displacement.dto.finder.DisplacementByIdFinderDto;
import com.epm.gestepm.masterdata.api.displacement.dto.updater.DisplacementUpdateDto;
import com.epm.gestepm.masterdata.api.displacement.exception.DisplacementNotFoundException;
import com.epm.gestepm.masterdata.api.displacement.service.DisplacementService;
import com.epm.gestepm.masterdata.displacement.dao.DisplacementDao;
import com.epm.gestepm.masterdata.displacement.dao.entity.Displacement;
import com.epm.gestepm.masterdata.displacement.dao.entity.creator.DisplacementCreate;
import com.epm.gestepm.masterdata.displacement.dao.entity.deleter.DisplacementDelete;
import com.epm.gestepm.masterdata.displacement.dao.entity.filter.DisplacementFilter;
import com.epm.gestepm.masterdata.displacement.dao.entity.finder.DisplacementByIdFinder;
import com.epm.gestepm.masterdata.displacement.dao.entity.updater.DisplacementUpdate;
import com.epm.gestepm.masterdata.displacement.service.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.masterdata.api.displacement.security.DisplacementPermission.PRMT_EDIT_D;
import static com.epm.gestepm.masterdata.api.displacement.security.DisplacementPermission.PRMT_READ_D;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service("displacementService")
@EnableExecutionLog(layerMarker = SERVICE)
public class DisplacementServiceImpl implements DisplacementService {

  private final DisplacementDao displacementDao;

  public DisplacementServiceImpl(DisplacementDao displacementDao) {
    this.displacementDao = displacementDao;
  }

  @Override
  @RequirePermits(value = PRMT_READ_D, action = "List displacements")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Listing displacements",
          msgOut = "Listing displacements OK",
          errorMsg = "Failed to list displacements")
  public List<DisplacementDto> list(DisplacementFilterDto filterDto) {

    final DisplacementFilter filter = getMapper(MapDToDisplacementFilter.class).from(filterDto);

    final List<Displacement> list = this.displacementDao.list(filter);

    return getMapper(MapDToDisplacementDto.class).from(list);
  }

  @Override
  @RequirePermits(value = PRMT_READ_D, action = "Page displacements")
  @LogExecution(operation = OP_READ,
      debugOut = true,
      msgIn = "Paginating displacements",
      msgOut = "Paginating displacements OK",
      errorMsg = "Failed to paginate displacements")
  public Page<DisplacementDto> list(DisplacementFilterDto filterDto, Long offset, Long limit) {

    final DisplacementFilter filter = getMapper(MapDToDisplacementFilter.class).from(filterDto);

    final Page<Displacement> page = this.displacementDao.list(filter, offset, limit);

    return getMapper(MapDToDisplacementDto.class).from(page);
  }

  @Override
  @RequirePermits(value = PRMT_READ_D, action = "Find displacement by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding displacement by ID, result can be empty",
          msgOut = "Found displacement by ID",
          errorMsg = "Failed to find displacement by ID")
  public Optional<DisplacementDto> find(DisplacementByIdFinderDto finderDto) {

    final DisplacementByIdFinder finder = getMapper(MapDToDisplacementByIdFinder.class).from(finderDto);

    final Optional<Displacement> found = this.displacementDao.find(finder);

    return found.map(getMapper(MapDToDisplacementDto.class)::from);
  }

  @Override
  @RequirePermits(value = PRMT_READ_D, action = "Find displacement by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding displacement by ID, result is expected or will fail",
          msgOut = "Found displacement by ID",
          errorMsg = "Activity center by ID not found")
  public DisplacementDto findOrNotFound(DisplacementByIdFinderDto finderDto) {

    final Supplier<RuntimeException> notFound = () -> new DisplacementNotFoundException(finderDto.getId());

    return this.find(finderDto).orElseThrow(notFound);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_D, action = "Create new displacement")
  @LogExecution(operation = OP_CREATE,
          debugOut = true,
          msgIn = "Creating new displacement",
          msgOut = "New displacement created OK",
          errorMsg = "Failed to create new displacement")
  public DisplacementDto create(DisplacementCreateDto createDto) {

    final DisplacementCreate create = getMapper(MapDToDisplacementCreate.class).from(createDto);

    final Displacement result = this.displacementDao.create(create);

    return getMapper(MapDToDisplacementDto.class).from(result);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_D, action = "Update displacement")
  @LogExecution(operation = OP_UPDATE,
          debugOut = true,
          msgIn = "Updating displacement",
          msgOut = "Activity center updated OK",
          errorMsg = "Failed to update displacement")
  public DisplacementDto update(DisplacementUpdateDto updateDto) {

    final DisplacementByIdFinderDto finderDto = new DisplacementByIdFinderDto();
    finderDto.setId(updateDto.getId());

    findOrNotFound(finderDto);

    final DisplacementUpdate update = getMapper(MapDToDisplacementUpdate.class).from(updateDto);

    final Displacement updated = this.displacementDao.update(update);

    return getMapper(MapDToDisplacementDto.class).from(updated);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_D, action = "Delete displacement")
  @LogExecution(operation = OP_DELETE,
          debugOut = true,
          msgIn = "Deleting displacement",
          msgOut = "Activity center deleted OK",
          errorMsg = "Failed to delete displacement")
  public void delete(DisplacementDeleteDto deleteDto) {

    final DisplacementByIdFinderDto finderDto = new DisplacementByIdFinderDto();
    finderDto.setId(deleteDto.getId());

    findOrNotFound(finderDto);

    final DisplacementDelete delete = getMapper(MapDToDisplacementDelete.class).from(deleteDto);

    this.displacementDao.delete(delete);
  }
}
