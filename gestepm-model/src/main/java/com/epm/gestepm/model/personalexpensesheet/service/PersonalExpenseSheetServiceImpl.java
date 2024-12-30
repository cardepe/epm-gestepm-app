package com.epm.gestepm.model.personalexpensesheet.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.personalexpensesheet.dao.PersonalExpenseSheetDao;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheet;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.creator.PersonalExpenseSheetCreate;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.deleter.PersonalExpenseSheetDelete;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.filter.PersonalExpenseSheetFilter;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.finder.PersonalExpenseSheetByIdFinder;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.updater.PersonalExpenseSheetUpdate;
import com.epm.gestepm.model.personalexpensesheet.service.mapper.*;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.creator.PersonalExpenseSheetCreateDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.deleter.PersonalExpenseSheetDeleteDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.filter.PersonalExpenseSheetFilterDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.finder.PersonalExpenseSheetByIdFinderDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.updater.PersonalExpenseSheetUpdateDto;
import com.epm.gestepm.modelapi.personalexpensesheet.exception.PersonalExpenseSheetNotFoundException;
import com.epm.gestepm.modelapi.personalexpensesheet.service.PersonalExpenseSheetService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.personalexpensesheet.security.PersonalExpenseSheetPermission.PRMT_EDIT_PES;
import static com.epm.gestepm.modelapi.personalexpensesheet.security.PersonalExpenseSheetPermission.PRMT_READ_PES;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service("personalExpenseSheetService")
@EnableExecutionLog(layerMarker = SERVICE)
public class PersonalExpenseSheetServiceImpl implements PersonalExpenseSheetService {

  private final PersonalExpenseSheetDao personalExpenseSheetDao;

  public PersonalExpenseSheetServiceImpl(PersonalExpenseSheetDao personalExpenseSheetDao) {
    this.personalExpenseSheetDao = personalExpenseSheetDao;
  }

  @Override
  @RequirePermits(value = PRMT_READ_PES, action = "List personal expense sheets")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Listing personal expense sheets",
          msgOut = "Listing personal expense sheets OK",
          errorMsg = "Failed to list personal expense sheets")
  public List<PersonalExpenseSheetDto> list(PersonalExpenseSheetFilterDto filterDto) {

    final PersonalExpenseSheetFilter filter = getMapper(MapPESToPersonalExpenseSheetFilter.class).from(filterDto);

    final List<PersonalExpenseSheet> list = this.personalExpenseSheetDao.list(filter);

    return getMapper(MapPESToPersonalExpenseSheetDto.class).from(list);
  }

  @Override
  @RequirePermits(value = PRMT_READ_PES, action = "Page personal expense sheets")
  @LogExecution(operation = OP_READ,
      debugOut = true,
      msgIn = "Paginating personal expense sheets",
      msgOut = "Paginating personal expense sheets OK",
      errorMsg = "Failed to paginate personal expense sheets")
  public Page<PersonalExpenseSheetDto> list(PersonalExpenseSheetFilterDto filterDto, Long offset, Long limit) {

    final PersonalExpenseSheetFilter filter = getMapper(MapPESToPersonalExpenseSheetFilter.class).from(filterDto);

    final Page<PersonalExpenseSheet> page = this.personalExpenseSheetDao.list(filter, offset, limit);

    return getMapper(MapPESToPersonalExpenseSheetDto.class).from(page);
  }

  @Override
  @RequirePermits(value = PRMT_READ_PES, action = "Find personal expense sheet by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding personal expense sheet by ID, result can be empty",
          msgOut = "Found personal expense sheet by ID",
          errorMsg = "Failed to find personal expense sheet by ID")
  public Optional<PersonalExpenseSheetDto> find(PersonalExpenseSheetByIdFinderDto finderDto) {

    final PersonalExpenseSheetByIdFinder finder = getMapper(MapPESToPersonalExpenseSheetByIdFinder.class).from(finderDto);

    final Optional<PersonalExpenseSheet> found = this.personalExpenseSheetDao.find(finder);

    return found.map(getMapper(MapPESToPersonalExpenseSheetDto.class)::from);
  }

  @Override
  @RequirePermits(value = PRMT_READ_PES, action = "Find personal expense sheet by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding personal expense sheet by ID, result is expected or will fail",
          msgOut = "Found personal expense sheet by ID",
          errorMsg = "Personal expense sheet by ID not found")
  public PersonalExpenseSheetDto findOrNotFound(PersonalExpenseSheetByIdFinderDto finderDto) {

    final Supplier<RuntimeException> notFound = () -> new PersonalExpenseSheetNotFoundException(finderDto.getId());

    return this.find(finderDto).orElseThrow(notFound);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_PES, action = "Create new personal expense sheet")
  @LogExecution(operation = OP_CREATE,
          debugOut = true,
          msgIn = "Creating new personal expense sheet",
          msgOut = "New personal expense sheet created OK",
          errorMsg = "Failed to create new personal expense sheet")
  public PersonalExpenseSheetDto create(PersonalExpenseSheetCreateDto createDto) {

    final PersonalExpenseSheetCreate create = getMapper(MapPESToPersonalExpenseSheetCreate.class).from(createDto);

    final PersonalExpenseSheet result = this.personalExpenseSheetDao.create(create);

    return getMapper(MapPESToPersonalExpenseSheetDto.class).from(result);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_PES, action = "Update personal expense sheet")
  @LogExecution(operation = OP_UPDATE,
          debugOut = true,
          msgIn = "Updating personal expense sheet",
          msgOut = "Personal expense sheet updated OK",
          errorMsg = "Failed to update personal expense sheet")
  public PersonalExpenseSheetDto update(PersonalExpenseSheetUpdateDto updateDto) {

    final PersonalExpenseSheetByIdFinderDto finderDto = new PersonalExpenseSheetByIdFinderDto();
    finderDto.setId(updateDto.getId());

    findOrNotFound(finderDto);

    final PersonalExpenseSheetUpdate update = getMapper(MapPESToPersonalExpenseSheetUpdate.class).from(updateDto);

    final PersonalExpenseSheet updated = this.personalExpenseSheetDao.update(update);

    return getMapper(MapPESToPersonalExpenseSheetDto.class).from(updated);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_PES, action = "Delete personal expense sheet")
  @LogExecution(operation = OP_DELETE,
          debugOut = true,
          msgIn = "Deleting personal expense sheet",
          msgOut = "Personal expense sheet deleted OK",
          errorMsg = "Failed to delete personal expense sheet")
  public void delete(PersonalExpenseSheetDeleteDto deleteDto) {

    final PersonalExpenseSheetByIdFinderDto finderDto = new PersonalExpenseSheetByIdFinderDto();
    finderDto.setId(deleteDto.getId());

    findOrNotFound(finderDto);

    final PersonalExpenseSheetDelete delete = getMapper(MapPESToPersonalExpenseSheetDelete.class).from(deleteDto);

    this.personalExpenseSheetDao.delete(delete);
  }
}
