package com.epm.gestepm.model.inspection.service;

import com.epm.gestepm.forum.model.api.service.TopicService;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.inspection.checker.InspectionChecker;
import com.epm.gestepm.model.inspection.dao.InspectionDao;
import com.epm.gestepm.model.inspection.dao.entity.Inspection;
import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionCreate;
import com.epm.gestepm.model.inspection.dao.entity.deleter.InspectionDelete;
import com.epm.gestepm.model.inspection.dao.entity.filter.InspectionFilter;
import com.epm.gestepm.model.inspection.dao.entity.finder.InspectionByIdFinder;
import com.epm.gestepm.model.inspection.dao.entity.updater.InspectionUpdate;
import com.epm.gestepm.model.inspection.service.mapper.*;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.CloseInspectionMailTemplateDto;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.deleter.InspectionDeleteDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFilterDto;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionByIdFinderDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import com.epm.gestepm.modelapi.inspection.exception.InspectionNotFoundException;
import com.epm.gestepm.modelapi.inspection.service.InspectionExportService;
import com.epm.gestepm.modelapi.inspection.service.InspectionService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.exception.ProjectByIdNotFoundException;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareStateEnumDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.UserByIdNotFoundException;
import com.epm.gestepm.modelapi.user.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_EDIT_I;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_READ_I;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@EnableExecutionLog(layerMarker = SERVICE)
public class InspectionServiceImpl implements InspectionService {

    private final HttpServletRequest request;

    private final InspectionChecker inspectionChecker;

    private final InspectionDao inspectionDao;

    private final InspectionExportService inspectionExportService;

    private final NoProgrammedShareService noProgrammedShareService;

    private final ProjectService projectService;

    private final SMTPService smtpService;

    private final TopicService topicService;

    private final UserService userService;

    public InspectionServiceImpl(HttpServletRequest request, InspectionChecker inspectionChecker, InspectionDao inspectionDao, InspectionExportService inspectionExportService, NoProgrammedShareService noProgrammedShareService, ProjectService projectService, SMTPService smtpService, TopicService topicService, UserService userService) {
        this.request = request;
        this.inspectionChecker = inspectionChecker;
        this.inspectionDao = inspectionDao;
        this.inspectionExportService = inspectionExportService;
        this.noProgrammedShareService = noProgrammedShareService;
        this.projectService = projectService;
        this.smtpService = smtpService;
        this.topicService = topicService;
        this.userService = userService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "List inspections")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing inspections",
            msgOut = "Listing inspections OK",
            errorMsg = "Failed to list inspections")
    public List<InspectionDto> list(InspectionFilterDto filterDto) {
        final InspectionFilter filter = getMapper(MapIToInspectionFilter.class).from(filterDto);

        final List<Inspection> page = this.inspectionDao.list(filter);

        return getMapper(MapIToInspectionDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "List inspections")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating inspections",
            msgOut = "Paginating inspections OK",
            errorMsg = "Failed to paginate inspections")
    public Page<InspectionDto> list(InspectionFilterDto filterDto, Long offset, Long limit) {
        final InspectionFilter filter = getMapper(MapIToInspectionFilter.class).from(filterDto);

        final Page<Inspection> page = this.inspectionDao.list(filter, offset, limit);

        return getMapper(MapIToInspectionDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Find inspection by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding inspection by ID, result can be empty",
            msgOut = "Found inspection by ID",
            errorMsg = "Failed to find inspection by ID")
    public Optional<InspectionDto> find(final InspectionByIdFinderDto finderDto) {
        final InspectionByIdFinder finder = getMapper(MapIToInspectionByIdFinder.class).from(finderDto);

        final Optional<Inspection> found = this.inspectionDao.find(finder);

        return found.map(getMapper(MapIToInspectionDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Find inspection by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding inspection by ID, result is expected or will fail",
            msgOut = "Found inspection by ID",
            errorMsg = "Intervention by ID not found")
    public InspectionDto findOrNotFound(final InspectionByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new InspectionNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_I, action = "Create new inspection")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new inspection",
            msgOut = "New country inspection OK",
            errorMsg = "Failed to create new inspection")
    public InspectionDto create(InspectionCreateDto createDto) {
        final NoProgrammedShareDto noProgrammedShare =
                this.noProgrammedShareService.findOrNotFound(new NoProgrammedShareByIdFinderDto(createDto.getShareId()));

        this.inspectionChecker.checker(noProgrammedShare, createDto);

        final InspectionCreate create = getMapper(MapIToInspectionCreate.class).from(createDto);
        create.setStartDate(LocalDateTime.now());

        final Inspection result = this.inspectionDao.create(create);

        this.updateNoProgrammedShare(noProgrammedShare.getId(), createDto.getFirstTechnicalId(), NoProgrammedShareStateEnumDto.IN_PROGRESS);

        return getMapper(MapIToInspectionDto.class).from(result);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_I, action = "Update inspection")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating inspection",
            msgOut = "Intervention updated OK",
            errorMsg = "Failed to update inspection")
    public InspectionDto update(InspectionUpdateDto updateDto) {
        final InspectionDto inspection = findOrNotFound(new InspectionByIdFinderDto(updateDto.getId()));
        final NoProgrammedShareDto noProgrammedShare = this.noProgrammedShareService.findOrNotFound(
                new NoProgrammedShareByIdFinderDto(inspection.getShareId()));

        this.inspectionChecker.checker(noProgrammedShare, inspection, updateDto);

        if (updateDto.getEndDate() == null) {
            updateDto.setEndDate(LocalDateTime.now());
        }

        final InspectionUpdate update = getMapper(MapIToInspectionUpdate.class).from(updateDto,
                getMapper(MapIToInspectionUpdate.class).from(inspection));

        final Inspection updated = this.inspectionDao.update(update);

        final InspectionDto result = getMapper(MapIToInspectionDto.class).from(updated);

        if (result.getTopicId() == null) {
            this.createForumComment(result, noProgrammedShare);
        }

        this.sendMail(result, updateDto.getNotify());

        return result;
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_I, action = "Delete inspection")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting inspection",
            msgOut = "Intervention deleted OK",
            errorMsg = "Failed to delete inspection")
    public void delete(InspectionDeleteDto deleteDto) {

        final InspectionByIdFinderDto finderDto = new InspectionByIdFinderDto();
        finderDto.setId(deleteDto.getId());

        findOrNotFound(finderDto);

        final InspectionDelete delete = getMapper(MapIToInspectionDelete.class).from(deleteDto);

        this.inspectionDao.delete(delete);
    }

    private void createForumComment(InspectionDto inspection, NoProgrammedShareDto noProgrammedShare) {

        if (noProgrammedShare.getTopicId() != null) {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(inspection.getDescription().replace("\n", "<br/>"));

            if (CollectionUtils.isNotEmpty(inspection.getMaterials())) {
                stringBuilder.append("<br/><br/>Materiales:<br/> <LIST><s>[list]</s>");

                inspection.getMaterials().forEach(material -> {
                    stringBuilder.append("<LI><s>[*]</s>").append(material.getDescription()).append("/").append(material.getUnits()).append(" uds (ref: ").append(material.getReference()).append(")</LI>");
                });

                stringBuilder.append("<e>[/list]</e></LIST>");
            }

            // TODO: add materials file, when refactor no URL enabled. should install documental gestor before...

            final String title = "Re: " + noProgrammedShare.getForumTitle();
            final String content = stringBuilder.toString();
            final String ip = request.getLocalAddr();

            final User user = this.userService.getUserById(Long.valueOf(inspection.getFirstTechnicalId()));

            // TODO: same with files, now null.

            this.topicService.create(title, content, noProgrammedShare.getTopicId().longValue(), ip, user.getUsername(), null)
                    .thenApply(topic -> {
                        final InspectionUpdate inspectionUpdate = getMapper(MapIToInspectionUpdate.class).from(inspection);
                        inspectionUpdate.setId(inspection.getId());
                        inspectionUpdate.setTopicId(topic.getId().intValue());

                        return this.inspectionDao.update(inspectionUpdate);
                    });
        }
    }

    private void updateNoProgrammedShare(final Integer id, final Integer userId, final NoProgrammedShareStateEnumDto state) {
        final NoProgrammedShareUpdateDto noProgrammedShareUpdateDto = new NoProgrammedShareUpdateDto();
        noProgrammedShareUpdateDto.setId(id);
        noProgrammedShareUpdateDto.setUserId(userId);
        noProgrammedShareUpdateDto.setState(state);

        this.noProgrammedShareService.update(noProgrammedShareUpdateDto);
    }

    private void sendMail(final InspectionDto inspection, final Boolean notify) {
        final byte[] pdf = this.inspectionExportService.generate(inspection);

        final Supplier<RuntimeException> userNotFound = () -> new UserByIdNotFoundException(inspection.getFirstTechnicalId());
        final User firstTechnical = Optional.ofNullable(this.userService.getUserById(inspection.getFirstTechnicalId().longValue()))
                .orElseThrow(userNotFound);

        final NoProgrammedShareDto noProgrammedShare = this.noProgrammedShareService
                .findOrNotFound(new NoProgrammedShareByIdFinderDto(inspection.getShareId()));

        final Supplier<RuntimeException> projectNotFound = () -> new ProjectByIdNotFoundException(noProgrammedShare.getProjectId());
        final Project project = Optional.ofNullable(this.projectService.getProjectById(noProgrammedShare.getProjectId().longValue()))
                .orElseThrow(projectNotFound);

        final CloseInspectionMailTemplateDto dto = new CloseInspectionMailTemplateDto();
        dto.setLocale(request.getLocale());
        dto.setEmail(firstTechnical.getEmail());
        dto.setNoProgrammedShare(noProgrammedShare);
        dto.setInspection(inspection);
        dto.setUser(firstTechnical);
        dto.setProject(project);
        dto.setPdf(pdf);

        this.smtpService.closeInspectionSendMail(dto);

        if (CollectionUtils.isNotEmpty(project.getResponsables())) {
            project.getResponsables().forEach(responsible -> {
                dto.setEmail(responsible.getEmail());
                this.smtpService.closeInspectionSendMail(dto);
            });
        }

        if (BooleanUtils.isTrue(notify) && project.getCustomer() != null) {
            final String mainEmail = project.getCustomer().getMainEmail();
            final String secondaryEmail = project.getCustomer().getSecondaryEmail();

            if (StringUtils.isNoneBlank(mainEmail)) {
                dto.setEmail(mainEmail);
                this.smtpService.closeInspectionSendMail(dto);
            }

            if (StringUtils.isNoneBlank(secondaryEmail)) {
                dto.setEmail(secondaryEmail);
                this.smtpService.closeInspectionSendMail(dto);
            }
        }
    }
}
