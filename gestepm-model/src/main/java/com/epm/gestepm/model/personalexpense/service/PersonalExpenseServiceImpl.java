package com.epm.gestepm.model.personalexpense.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.personalexpense.dao.PersonalExpenseDao;
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpense;
import com.epm.gestepm.model.personalexpense.dao.entity.creator.PersonalExpenseCreate;
import com.epm.gestepm.model.personalexpense.dao.entity.deleter.PersonalExpenseDelete;
import com.epm.gestepm.model.personalexpense.dao.entity.filter.PersonalExpenseFilter;
import com.epm.gestepm.model.personalexpense.dao.entity.finder.PersonalExpenseByIdFinder;
import com.epm.gestepm.model.personalexpense.dao.entity.updater.PersonalExpenseUpdate;
import com.epm.gestepm.model.personalexpense.service.mapper.*;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseDto;
import com.epm.gestepm.modelapi.personalexpense.dto.creator.PersonalExpenseCreateDto;
import com.epm.gestepm.modelapi.personalexpense.dto.deleter.PersonalExpenseDeleteDto;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFilterDto;
import com.epm.gestepm.modelapi.personalexpense.dto.finder.PersonalExpenseByIdFinderDto;
import com.epm.gestepm.modelapi.personalexpense.dto.updater.PersonalExpenseUpdateDto;
import com.epm.gestepm.modelapi.personalexpense.exception.PersonalExpenseNotFoundException;
import com.epm.gestepm.modelapi.personalexpense.service.PersonalExpenseService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.personalexpense.security.PersonalExpensePermission.PRMT_EDIT_PE;
import static com.epm.gestepm.modelapi.personalexpense.security.PersonalExpensePermission.PRMT_READ_PE;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service("personalExpenseService")
@EnableExecutionLog(layerMarker = SERVICE)
public class PersonalExpenseServiceImpl implements PersonalExpenseService {

  private final PersonalExpenseDao personalExpenseDao;

  public PersonalExpenseServiceImpl(PersonalExpenseDao personalExpenseDao) {
    this.personalExpenseDao = personalExpenseDao;
  }

  @Override
  @RequirePermits(value = PRMT_READ_PE, action = "List personal expenses")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Listing personal expenses",
          msgOut = "Listing personal expenses OK",
          errorMsg = "Failed to list personal expenses")
  public List<PersonalExpenseDto> list(PersonalExpenseFilterDto filterDto) {

    final PersonalExpenseFilter filter = getMapper(MapPEToPersonalExpenseFilter.class).from(filterDto);

    final List<PersonalExpense> list = this.personalExpenseDao.list(filter);

    return getMapper(MapPEToPersonalExpenseDto.class).from(list);
  }

  @Override
  @RequirePermits(value = PRMT_READ_PE, action = "Page personal expenses")
  @LogExecution(operation = OP_READ,
      debugOut = true,
      msgIn = "Paginating personal expenses",
      msgOut = "Paginating personal expenses OK",
      errorMsg = "Failed to paginate personal expenses")
  public Page<PersonalExpenseDto> list(PersonalExpenseFilterDto filterDto, Long offset, Long limit) {

    final PersonalExpenseFilter filter = getMapper(MapPEToPersonalExpenseFilter.class).from(filterDto);

    final Page<PersonalExpense> page = this.personalExpenseDao.list(filter, offset, limit);

    return getMapper(MapPEToPersonalExpenseDto.class).from(page);
  }

  @Override
  @RequirePermits(value = PRMT_READ_PE, action = "Find personal expense by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding personal expense by ID, result can be empty",
          msgOut = "Found personal expense by ID",
          errorMsg = "Failed to find personal expense by ID")
  public Optional<PersonalExpenseDto> find(PersonalExpenseByIdFinderDto finderDto) {

    final PersonalExpenseByIdFinder finder = getMapper(MapPEToPersonalExpenseByIdFinder.class).from(finderDto);

    final Optional<PersonalExpense> found = this.personalExpenseDao.find(finder);

    return found.map(getMapper(MapPEToPersonalExpenseDto.class)::from);
  }

  @Override
  @RequirePermits(value = PRMT_READ_PE, action = "Find personal expense by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding personal expense by ID, result is expected or will fail",
          msgOut = "Found personal expense by ID",
          errorMsg = "Personal expense by ID not found")
  public PersonalExpenseDto findOrNotFound(PersonalExpenseByIdFinderDto finderDto) {

    final Supplier<RuntimeException> notFound = () -> new PersonalExpenseNotFoundException(finderDto.getId());

    return this.find(finderDto).orElseThrow(notFound);
  }

  @Override
  @Transactional
  @RequirePermits(value = PRMT_EDIT_PE, action = "Create new personal expense")
  @LogExecution(operation = OP_CREATE,
          debugOut = true,
          msgIn = "Creating new personal expense",
          msgOut = "New personal expense created OK",
          errorMsg = "Failed to create new personal expense")
  public PersonalExpenseDto create(PersonalExpenseCreateDto createDto) {

    final PersonalExpenseCreate create = getMapper(MapPEToPersonalExpenseCreate.class).from(createDto);
    create.setNoticeDate(LocalDateTime.now());

    final PersonalExpense result = this.personalExpenseDao.create(create);

    return getMapper(MapPEToPersonalExpenseDto.class).from(result);
  }

  @Override
  @Transactional
  @RequirePermits(value = PRMT_EDIT_PE, action = "Update personal expense")
  @LogExecution(operation = OP_UPDATE,
          debugOut = true,
          msgIn = "Updating personal expense",
          msgOut = "Personal expense updated OK",
          errorMsg = "Failed to update personal expense")
  public PersonalExpenseDto update(PersonalExpenseUpdateDto updateDto) {

    final PersonalExpenseByIdFinderDto finderDto = new PersonalExpenseByIdFinderDto();
    finderDto.setId(updateDto.getId());

    final PersonalExpenseDto expense = findOrNotFound(finderDto);

    final PersonalExpenseUpdate update = getMapper(MapPEToPersonalExpenseUpdate.class).from(updateDto,
            getMapper(MapPEToPersonalExpenseUpdate.class).from(expense));

    final PersonalExpense updated = this.personalExpenseDao.update(update);

    return getMapper(MapPEToPersonalExpenseDto.class).from(updated);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_PE, action = "Delete personal expense")
  @LogExecution(operation = OP_DELETE,
          debugOut = true,
          msgIn = "Deleting personal expense",
          msgOut = "Personal expense deleted OK",
          errorMsg = "Failed to delete personal expense")
  public void delete(PersonalExpenseDeleteDto deleteDto) {

    final PersonalExpenseByIdFinderDto finderDto = new PersonalExpenseByIdFinderDto();
    finderDto.setId(deleteDto.getId());

    findOrNotFound(finderDto);

    final PersonalExpenseDelete delete = getMapper(MapPEToPersonalExpenseDelete.class).from(deleteDto);

    this.personalExpenseDao.delete(delete);
  }
}
