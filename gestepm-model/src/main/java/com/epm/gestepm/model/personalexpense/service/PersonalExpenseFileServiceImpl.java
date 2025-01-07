package com.epm.gestepm.model.personalexpense.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.model.personalexpense.dao.PersonalExpenseFileDao;
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpenseFile;
import com.epm.gestepm.model.personalexpense.dao.entity.creator.PersonalExpenseFileCreate;
import com.epm.gestepm.model.personalexpense.dao.entity.filter.PersonalExpenseFileFilter;
import com.epm.gestepm.model.personalexpense.dao.entity.finder.PersonalExpenseFileByIdFinder;
import com.epm.gestepm.model.personalexpense.service.mapper.MapPEFToPersonalExpenseFileByIdFinder;
import com.epm.gestepm.model.personalexpense.service.mapper.MapPEFToPersonalExpenseFileCreate;
import com.epm.gestepm.model.personalexpense.service.mapper.MapPEFToPersonalExpenseFileDto;
import com.epm.gestepm.model.personalexpense.service.mapper.MapPEFToPersonalExpenseFileFilter;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseFileDto;
import com.epm.gestepm.modelapi.personalexpense.dto.creator.PersonalExpenseFileCreateDto;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFileFilterDto;
import com.epm.gestepm.modelapi.personalexpense.dto.finder.PersonalExpenseFileByIdFinderDto;
import com.epm.gestepm.modelapi.personalexpense.exception.PersonalExpenseFileNotFoundException;
import com.epm.gestepm.modelapi.personalexpense.service.PersonalExpenseFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_CREATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;
import static com.epm.gestepm.modelapi.personalexpense.security.PersonalExpensePermission.PRMT_EDIT_PE;
import static com.epm.gestepm.modelapi.personalexpense.security.PersonalExpensePermission.PRMT_READ_PE;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@EnableExecutionLog(layerMarker = SERVICE)
public class PersonalExpenseFileServiceImpl implements PersonalExpenseFileService {

    private final PersonalExpenseFileDao personalExpenseFileDao;

    public PersonalExpenseFileServiceImpl(PersonalExpenseFileDao personalExpenseFileDao) {
        this.personalExpenseFileDao = personalExpenseFileDao;
    }

    @Override
    @RequirePermits(value = PRMT_READ_PE, action = "List personal expense files")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating personal expense file files",
            msgOut = "Paginating personal expense file files OK",
            errorMsg = "Failed to paginate personal expense file files")
    public List<PersonalExpenseFileDto> list(PersonalExpenseFileFilterDto filterDto) {
        final PersonalExpenseFileFilter filter = getMapper(MapPEFToPersonalExpenseFileFilter.class).from(filterDto);

        final List<PersonalExpenseFile> page = this.personalExpenseFileDao.list(filter);

        return getMapper(MapPEFToPersonalExpenseFileDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PE, action = "Find personal expense file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding personal expense file by ID, result can be empty",
            msgOut = "Found personal expense file by ID",
            errorMsg = "Failed to find personal expense file by ID")
    public Optional<PersonalExpenseFileDto> find(final PersonalExpenseFileByIdFinderDto finderDto) {
        final PersonalExpenseFileByIdFinder finder = getMapper(MapPEFToPersonalExpenseFileByIdFinder.class).from(finderDto);

        final Optional<PersonalExpenseFile> found = this.personalExpenseFileDao.find(finder);

        return found.map(getMapper(MapPEFToPersonalExpenseFileDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PE, action = "Find personal expense file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding personal expense file by ID, result is expected or will fail",
            msgOut = "Found personal expense file by ID",
            errorMsg = "No programmed share by ID not found")
    public PersonalExpenseFileDto findOrNotFound(final PersonalExpenseFileByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new PersonalExpenseFileNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_PE, action = "Create new personal expense file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new personal expense file",
            msgOut = "New personal expense file OK",
            errorMsg = "Failed to create new personal expense file")
    public PersonalExpenseFileDto create(PersonalExpenseFileCreateDto createDto) {
        final PersonalExpenseFileCreate create = getMapper(MapPEFToPersonalExpenseFileCreate.class).from(createDto);

        final PersonalExpenseFile result = this.personalExpenseFileDao.create(create);

        return getMapper(MapPEFToPersonalExpenseFileDto.class).from(result);
    }

}
