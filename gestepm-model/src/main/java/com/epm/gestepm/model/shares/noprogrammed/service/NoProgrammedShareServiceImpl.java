package com.epm.gestepm.model.shares.noprogrammed.service;

import com.epm.gestepm.forum.model.api.service.TopicService;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.noprogrammed.dao.NoProgrammedShareDao;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShareStateEnum;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareCreate;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.deleter.NoProgrammedShareDelete;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFilter;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareByIdFinder;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.NoProgrammedShareUpdate;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.*;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.family.dto.Family;
import com.epm.gestepm.modelapi.family.service.FamilyService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareCreateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.deleter.NoProgrammedShareDeleteDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter.NoProgrammedShareFilterDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.exception.NoProgrammedShareNotFoundException;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.shares.noprogrammed.security.NoProgrammedSharePermission.PRMT_EDIT_NPS;
import static com.epm.gestepm.modelapi.shares.noprogrammed.security.NoProgrammedSharePermission.PRMT_READ_NPS;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@EnableExecutionLog(layerMarker = SERVICE)
public class NoProgrammedShareServiceImpl implements NoProgrammedShareService {

    // private final FamilyService familyService;

    private final NoProgrammedShareDao noProgrammedShareDao;

    // private final ProjectService projectService;

    // private final SubFamilyService subFamilyService;

    // private final TopicService topicService;

    public NoProgrammedShareServiceImpl(NoProgrammedShareDao noProgrammedShareDao) {
        this.noProgrammedShareDao = noProgrammedShareDao;
    }

    /*public NoProgrammedShareServiceImpl(FamilyService familyService, NoProgrammedShareDao noProgrammedShareDao,
                                        ProjectService projectService, SubFamilyService subFamilyService,
                                        TopicService topicService) {
        this.familyService = familyService;
        this.noProgrammedShareDao = noProgrammedShareDao;
        this.projectService = projectService;
        this.subFamilyService = subFamilyService;
        this.topicService = topicService;
    }
    */

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "List countries")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating no programmed shares",
            msgOut = "Paginating no programmed shares OK",
            errorMsg = "Failed to paginate no programmed shares")
    public Page<NoProgrammedShareDto> list(NoProgrammedShareFilterDto filterDto, Long offset, Long limit) {

        final NoProgrammedShareFilter filter = getMapper(MapNPSToNoProgrammedShareFilter.class).from(filterDto);

        final Page<NoProgrammedShare> page = this.noProgrammedShareDao.list(filter, offset, limit);

        return getMapper(MapNPSToNoProgrammedShareDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "Find no programmed share by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding no programmed share by ID, result can be empty",
            msgOut = "Found no programmed share by ID",
            errorMsg = "Failed to find no programmed share by ID")
    public Optional<NoProgrammedShareDto> find(final NoProgrammedShareByIdFinderDto finderDto) {

        final NoProgrammedShareByIdFinder finder = getMapper(MapNPSToNoProgrammedShareByIdFinder.class).from(finderDto);

        final Optional<NoProgrammedShare> found = this.noProgrammedShareDao.find(finder);

        return found.map(getMapper(MapNPSToNoProgrammedShareDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "Find no programmed share by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding no programmed share by ID, result is expected or will fail",
            msgOut = "Found no programmed share by ID",
            errorMsg = "No programmed share by ID not found")
    public NoProgrammedShareDto findOrNotFound(final NoProgrammedShareByIdFinderDto finderDto) {

        final Supplier<RuntimeException> notFound = () -> new NoProgrammedShareNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_NPS, action = "Create new no programmed share")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new no programmed share",
            msgOut = "New country no programmed share OK",
            errorMsg = "Failed to create new no programmed share")
    public NoProgrammedShareDto create(NoProgrammedShareCreateDto createDto) {

        // TODO: check if userId exists
        // TODO: check if projectId exists

        final NoProgrammedShareCreate create = getMapper(MapNPSToNoProgrammedShareCreate.class).from(createDto);
        create.setStartDate(OffsetDateTime.now());
        // create.setTopicId();
        // create.setForumTitle();
        create.setState(NoProgrammedShareStateEnum.NEW);

        final NoProgrammedShare result = this.noProgrammedShareDao.create(create);

        if (result != null) {
            // TODO: appendFiles
        }

        return getMapper(MapNPSToNoProgrammedShareDto.class).from(result);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_NPS, action = "Update no programmed share")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating no programmed share",
            msgOut = "No programmed share updated OK",
            errorMsg = "Failed to update no programmed share")
    public NoProgrammedShareDto update(NoProgrammedShareUpdateDto updateDto) {

        final NoProgrammedShareByIdFinderDto finderDto = new NoProgrammedShareByIdFinderDto();
        finderDto.setId(updateDto.getId());

        findOrNotFound(finderDto);

        final NoProgrammedShareUpdate update = getMapper(MapNPSToNoProgrammedShareUpdate.class).from(updateDto);

        final NoProgrammedShare updated = this.noProgrammedShareDao.update(update);

        return getMapper(MapNPSToNoProgrammedShareDto.class).from(updated);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_NPS, action = "Delete no programmed share")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting no programmed share",
            msgOut = "No programmed share deleted OK",
            errorMsg = "Failed to delete no programmed share")
    public void delete(NoProgrammedShareDeleteDto deleteDto) {

        final NoProgrammedShareByIdFinderDto finderDto = new NoProgrammedShareByIdFinderDto();
        finderDto.setId(deleteDto.getId());

        findOrNotFound(finderDto);

        final NoProgrammedShareDelete delete = getMapper(MapNPSToNoProgrammedShareDelete.class).from(deleteDto);

        this.noProgrammedShareDao.delete(delete);
    }

    // FIXME: change to forum module.
    /*
    private void generateForumTitle(final NoProgrammedShareCreateDto createDto) {

        final Family family = this.familyService.getById(createDto.getFamilyId().longValue());
        final Locale locale = new Locale("");
        final Project project = this.projectService.getProjectById(createDto.getProjectId().longValue());
        final SubFamily subFamily = this.subFamilyService.getById(createDto.getSubFamilyId().longValue());

        if (project.getForumId() != null) {

            final String familyName = ("es".equalsIgnoreCase(locale.getLanguage()) ? family.getNameES() : family.getNameFR())
                    + (StringUtils.isNoneBlank(family.getBrand()) ? " " + family.getBrand() : "")
                    + (StringUtils.isNoneBlank(family.getModel()) ? " " + family.getModel() : "")
                    + (StringUtils.isNoneBlank(family.getEnrollment()) ? " " + family.getEnrollment() : "");

            final String forumTitle = share.getId() + " " + Utiles.getDateFormattedForForum(share.getNoticeDate()) + " " + familyStr + " " + ("es".equals(locale.getLanguage()) ? share.getSubFamily().getNameES() : share.getSubFamily().getNameFR());

        }
    }
    */
}
