package com.epm.gestepm.masterdata.country.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.country.dto.CountryDto;
import com.epm.gestepm.masterdata.api.country.dto.creator.CountryCreateDto;
import com.epm.gestepm.masterdata.api.country.dto.deleter.CountryDeleteDto;
import com.epm.gestepm.masterdata.api.country.dto.filter.CountryFilterDto;
import com.epm.gestepm.masterdata.api.country.dto.finder.CountryByIdFinderDto;
import com.epm.gestepm.masterdata.api.country.dto.updater.CountryUpdateDto;
import com.epm.gestepm.masterdata.api.country.exception.CountryNotFoundException;
import com.epm.gestepm.masterdata.api.country.service.CountryService;
import com.epm.gestepm.masterdata.country.dao.CountryDao;
import com.epm.gestepm.masterdata.country.dao.entity.Country;
import com.epm.gestepm.masterdata.country.dao.entity.creator.CountryCreate;
import com.epm.gestepm.masterdata.country.dao.entity.deleter.CountryDelete;
import com.epm.gestepm.masterdata.country.dao.entity.filter.CountryFilter;
import com.epm.gestepm.masterdata.country.dao.entity.finder.CountryByIdFinder;
import com.epm.gestepm.masterdata.country.dao.entity.updater.CountryUpdate;
import com.epm.gestepm.masterdata.country.service.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.masterdata.api.country.security.CountryPermission.PRMT_EDIT_C;
import static com.epm.gestepm.masterdata.api.country.security.CountryPermission.PRMT_READ_C;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@EnableExecutionLog(layerMarker = SERVICE)
public class CountryServiceImpl implements CountryService {

  private final CountryDao countryDao;

  public CountryServiceImpl(CountryDao countryDao) {
    this.countryDao = countryDao;
  }

  @Override
  @RequirePermits(value = PRMT_READ_C, action = "List countries")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Listing countries",
          msgOut = "Listing countries OK",
          errorMsg = "Failed to list countries")
  public List<CountryDto> list(CountryFilterDto filterDto) {

    final CountryFilter filter = getMapper(MapCToCountryFilter.class).from(filterDto);

    final List<Country> page = this.countryDao.list(filter);

    return getMapper(MapCToCountryDto.class).from(page);
  }

  @Override
  @RequirePermits(value = PRMT_READ_C, action = "Page countries")
  @LogExecution(operation = OP_READ,
      debugOut = true,
      msgIn = "Paginating countries",
      msgOut = "Paginating countries OK",
      errorMsg = "Failed to paginate countries")
  public Page<CountryDto> list(CountryFilterDto filterDto, Long offset, Long limit) {

    final CountryFilter filter = getMapper(MapCToCountryFilter.class).from(filterDto);

    final Page<Country> page = this.countryDao.list(filter, offset, limit);

    return getMapper(MapCToCountryDto.class).from(page);
  }

  @Override
  @RequirePermits(value = PRMT_READ_C, action = "Find country by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding country by ID, result can be empty",
          msgOut = "Found country by ID",
          errorMsg = "Failed to find country by ID")
  public Optional<CountryDto> find(CountryByIdFinderDto finderDto) {

    final CountryByIdFinder finder = getMapper(MapCToCountryByIdFinder.class).from(finderDto);

    final Optional<Country> found = this.countryDao.find(finder);

    return found.map(getMapper(MapCToCountryDto.class)::from);
  }

  @Override
  @RequirePermits(value = PRMT_READ_C, action = "Find country by ID")
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Finding country by ID, result is expected or will fail",
          msgOut = "Found country by ID",
          errorMsg = "Country by ID not found")
  public CountryDto findOrNotFound(CountryByIdFinderDto finderDto) {

    final Supplier<RuntimeException> notFound = () -> new CountryNotFoundException(finderDto.getId());

    return this.find(finderDto).orElseThrow(notFound);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_C, action = "Create new country")
  @LogExecution(operation = OP_CREATE,
          debugOut = true,
          msgIn = "Creating new country",
          msgOut = "New country created OK",
          errorMsg = "Failed to create new country")
  public CountryDto create(CountryCreateDto createDto) {

    final CountryCreate create = getMapper(MapCToCountryCreate.class).from(createDto);

    final Country result = this.countryDao.create(create);

    return getMapper(MapCToCountryDto.class).from(result);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_C, action = "Update country")
  @LogExecution(operation = OP_UPDATE,
          debugOut = true,
          msgIn = "Updating country",
          msgOut = "Country updated OK",
          errorMsg = "Failed to update country")
  public CountryDto update(CountryUpdateDto updateDto) {

    final CountryByIdFinderDto finderDto = new CountryByIdFinderDto();
    finderDto.setId(updateDto.getId());

    findOrNotFound(finderDto);

    final CountryUpdate update = getMapper(MapCToCountryUpdate.class).from(updateDto);

    final Country updated = this.countryDao.update(update);

    return getMapper(MapCToCountryDto.class).from(updated);
  }

  @Override
  @RequirePermits(value = PRMT_EDIT_C, action = "Delete country")
  @LogExecution(operation = OP_DELETE,
          debugOut = true,
          msgIn = "Deleting country",
          msgOut = "Country deleted OK",
          errorMsg = "Failed to delete country")
  public void delete(CountryDeleteDto deleteDto) {

    final CountryByIdFinderDto finderDto = new CountryByIdFinderDto();
    finderDto.setId(deleteDto.getId());

    findOrNotFound(finderDto);

    final CountryDelete delete = getMapper(MapCToCountryDelete.class).from(deleteDto);

    this.countryDao.delete(delete);
  }

}
