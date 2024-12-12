package com.epm.gestepm.model.inspection.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.model.inspection.dao.InspectionFileDao;
import com.epm.gestepm.model.inspection.dao.entity.InspectionFile;
import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionFileCreate;
import com.epm.gestepm.model.inspection.dao.entity.filter.InspectionFileFilter;
import com.epm.gestepm.model.inspection.dao.entity.finder.InspectionFileByIdFinder;
import com.epm.gestepm.model.inspection.service.mapper.MapIFToInspectionFileByIdFinder;
import com.epm.gestepm.model.inspection.service.mapper.MapIFToInspectionFileCreate;
import com.epm.gestepm.model.inspection.service.mapper.MapIFToInspectionFileDto;
import com.epm.gestepm.model.inspection.service.mapper.MapIFToInspectionFileFilter;
import com.epm.gestepm.modelapi.inspection.dto.InspectionFileDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionFileCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFileFilterDto;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionFileByIdFinderDto;
import com.epm.gestepm.modelapi.inspection.exception.InspectionFileNotFoundException;
import com.epm.gestepm.modelapi.inspection.service.InspectionFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_CREATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_EDIT_I;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_READ_I;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@EnableExecutionLog(layerMarker = SERVICE)
public class InspectionFileServiceImpl implements InspectionFileService {

    private final InspectionFileDao noProgrammedShareDao;

    public InspectionFileServiceImpl(InspectionFileDao noProgrammedShareDao) {
        this.noProgrammedShareDao = noProgrammedShareDao;
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "List countries")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating no programmed share file files",
            msgOut = "Paginating no programmed share file files OK",
            errorMsg = "Failed to paginate no programmed share file files")
    public List<InspectionFileDto> list(InspectionFileFilterDto filterDto) {
        final InspectionFileFilter filter = getMapper(MapIFToInspectionFileFilter.class).from(filterDto);

        final List<InspectionFile> page = this.noProgrammedShareDao.list(filter);

        return getMapper(MapIFToInspectionFileDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Find no programmed share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding no programmed share file by ID, result can be empty",
            msgOut = "Found no programmed share file by ID",
            errorMsg = "Failed to find no programmed share file by ID")
    public Optional<InspectionFileDto> find(final InspectionFileByIdFinderDto finderDto) {
        final InspectionFileByIdFinder finder = getMapper(MapIFToInspectionFileByIdFinder.class).from(finderDto);

        final Optional<InspectionFile> found = this.noProgrammedShareDao.find(finder);

        return found.map(getMapper(MapIFToInspectionFileDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Find no programmed share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding no programmed share file by ID, result is expected or will fail",
            msgOut = "Found no programmed share file by ID",
            errorMsg = "No programmed share by ID not found")
    public InspectionFileDto findOrNotFound(final InspectionFileByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new InspectionFileNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_I, action = "Create new no programmed share file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new no programmed share file",
            msgOut = "New country no programmed share file OK",
            errorMsg = "Failed to create new no programmed share file")
    public InspectionFileDto create(InspectionFileCreateDto createDto) {
        final InspectionFileCreate create = getMapper(MapIFToInspectionFileCreate.class).from(createDto);

        final InspectionFile result = this.noProgrammedShareDao.create(create);

        return getMapper(MapIFToInspectionFileDto.class).from(result);
    }

}
