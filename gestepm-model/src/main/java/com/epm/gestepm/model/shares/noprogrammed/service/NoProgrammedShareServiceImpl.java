package com.epm.gestepm.model.shares.noprogrammed.service;

import com.epm.gestepm.forum.model.api.service.TopicService;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.noprogrammed.dao.NoProgrammedShareDao;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareCreate;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.deleter.NoProgrammedShareDelete;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFilter;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareByIdFinder;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.NoProgrammedShareUpdate;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.*;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.CloseNoProgrammedShareMailTemplateDto;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.OpenNoProgrammedShareMailTemplateDto;
import com.epm.gestepm.modelapi.family.dto.Family;
import com.epm.gestepm.modelapi.family.service.FamilyService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.exception.ProjectByIdNotFoundException;
import com.epm.gestepm.modelapi.project.exception.ProjectIsNotStationException;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareStateEnumDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareCreateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.deleter.NoProgrammedShareDeleteDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter.NoProgrammedShareFilterDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.exception.NoProgrammedShareForbiddenException;
import com.epm.gestepm.modelapi.shares.noprogrammed.exception.NoProgrammedShareNotFoundException;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.UserByIdNotFoundException;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.service.UserSigningService;
import com.itextpdf.xmp.impl.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.shares.noprogrammed.security.NoProgrammedSharePermission.PRMT_EDIT_NPS;
import static com.epm.gestepm.modelapi.shares.noprogrammed.security.NoProgrammedSharePermission.PRMT_READ_NPS;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@EnableExecutionLog(layerMarker = SERVICE)
public class NoProgrammedShareServiceImpl implements NoProgrammedShareService {

    private final FamilyService familyService;

    private final HttpServletRequest request;

    private final NoProgrammedShareDao noProgrammedShareDao;

    private final ProjectService projectService;

    private final SMTPService smtpService;

    private final SubFamilyService subFamilyService;

    private final TopicService topicService;

    private final UserService userService;

    private final UserSigningService userSigningService;

    public NoProgrammedShareServiceImpl(FamilyService familyService, HttpServletRequest request, NoProgrammedShareDao noProgrammedShareDao, ProjectService projectService, SMTPService smtpService, SubFamilyService subFamilyService, TopicService topicService, UserService userService, UserSigningService userSigningService) {
        this.familyService = familyService;
        this.request = request;
        this.noProgrammedShareDao = noProgrammedShareDao;
        this.projectService = projectService;
        this.smtpService = smtpService;
        this.subFamilyService = subFamilyService;
        this.topicService = topicService;
        this.userService = userService;
        this.userSigningService = userSigningService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "List no programmed shares")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing no programmed shares",
            msgOut = "Listing no programmed shares OK",
            errorMsg = "Failed to list no programmed shares")
    public List<NoProgrammedShareDto> list(NoProgrammedShareFilterDto filterDto) {
        final NoProgrammedShareFilter filter = getMapper(MapNPSToNoProgrammedShareFilter.class).from(filterDto);

        final List<NoProgrammedShare> list = this.noProgrammedShareDao.list(filter);

        return getMapper(MapNPSToNoProgrammedShareDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "List no programmed shares")
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
            msgOut = "New no programmed share created OK",
            errorMsg = "Failed to create new no programmed share")
    public NoProgrammedShareDto create(NoProgrammedShareCreateDto createDto) {
        this.checker(createDto.getUserId(), createDto.getProjectId(), createDto);

        final NoProgrammedShareCreate create = getMapper(MapNPSToNoProgrammedShareCreate.class).from(createDto);
        create.setStartDate(OffsetDateTime.now());

        final NoProgrammedShare result = this.noProgrammedShareDao.create(create);

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
        final NoProgrammedShareByIdFinderDto finderDto = new NoProgrammedShareByIdFinderDto(updateDto.getId());

        final NoProgrammedShareDto noProgrammedShareDto = findOrNotFound(finderDto);

        this.checker(noProgrammedShareDto.getUserId(), noProgrammedShareDto.getProjectId(), null);

        if (NoProgrammedShareStateEnumDto.CLOSED.equals(updateDto.getState())) {
            updateDto.setEndDate(OffsetDateTime.now());
        }

        final NoProgrammedShareUpdate update = getMapper(MapNPSToNoProgrammedShareUpdate.class).from(updateDto,
                getMapper(MapNPSToNoProgrammedShareUpdate.class).from(noProgrammedShareDto));

        final NoProgrammedShare updated = this.noProgrammedShareDao.update(update);

        final NoProgrammedShareDto result = getMapper(MapNPSToNoProgrammedShareDto.class).from(updated);

        if (result.getTopicId() == null) {
            this.createForumEntryAndUpdate(result, update);
        }

        if (NoProgrammedShareStateEnumDto.CLOSED.equals(updateDto.getState())) {
            this.sendMailFinal(result);
        }

        return result;
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_NPS, action = "Delete no programmed share")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting no programmed share",
            msgOut = "No programmed share deleted OK",
            errorMsg = "Failed to delete no programmed share")
    public void delete(NoProgrammedShareDeleteDto deleteDto) {

        final NoProgrammedShareByIdFinderDto finderDto = new NoProgrammedShareByIdFinderDto(deleteDto.getId());

        findOrNotFound(finderDto);

        final NoProgrammedShareDelete delete = getMapper(MapNPSToNoProgrammedShareDelete.class).from(deleteDto);

        this.noProgrammedShareDao.delete(delete);
    }

    private <T> void checker(final Integer userId, final Integer projectId, final NoProgrammedShareCreateDto dto) {
        final Supplier<RuntimeException> userNotFound = () -> new UserByIdNotFoundException(userId);
        final User user = Optional.ofNullable(this.userService.getUserById(userId.longValue()))
                .orElseThrow(userNotFound);

        final UserSigning userSigning = this.userSigningService.getByUserIdAndEndDate(userId.longValue(), null);

        if (userSigning == null && !Utiles.havePrivileges(user.getSubRole().getRol())) {
            throw new NoProgrammedShareForbiddenException(userId, user.getSubRole().getRol());
        }

        if (userSigning != null && dto != null) {
            dto.setUserSigningId(userId);
        }

        final Supplier<RuntimeException> projectNotFound = () -> new ProjectByIdNotFoundException(projectId);
        final Project project = Optional.ofNullable(this.projectService.getProjectById(projectId.longValue()))
                .orElseThrow(projectNotFound);

        if (project.getStation() != 1) {
            throw new ProjectIsNotStationException(project.getId().intValue());
        }
    }

    private void createForumEntryAndUpdate(final NoProgrammedShareDto noProgrammedShare, NoProgrammedShareUpdate update) {
        final Project project = this.projectService.getProjectById(Long.valueOf(noProgrammedShare.getProjectId()));
        final Long forumId = project.getForumId();
        final String forumTitle = this.getForumTitle(update);
        final String ip = request.getLocalAddr();
        final List<MultipartFile> files = CollectionUtils.isNotEmpty(update.getFiles())
                ? update.getFiles().stream()
                .map(file -> convertToMultipartFile(file.getName() + "." + file.getExt(), Base64.decode(file.getContent()).getBytes()))
                .collect(Collectors.toList())
                : new ArrayList<>();

        final User user = this.userService.getUserById(Long.valueOf(noProgrammedShare.getUserId()));

        topicService.create(forumTitle, update.getDescription(), forumId, ip, user.getUsername(), files)
                .thenApply(topic -> {
                    update.setId(noProgrammedShare.getId());
                    update.setTopicId(topic.getId().intValue());
                    update.setForumTitle(forumTitle);

                    return this.noProgrammedShareDao.update(update);
                });

        final NoProgrammedShareUpdateDto dto = getMapper(MapNPSToNoProgrammedShareUpdateDto.class).from(update);

        final OpenNoProgrammedShareMailTemplateDto template = new OpenNoProgrammedShareMailTemplateDto();
        template.setLocale(request.getLocale());
        template.setNoProgrammedShare(getMapper(MapNPSToNoProgrammedShareUpdateDto.class).from(update));
        template.setEmail(user.getEmail());
        template.setUser(user);
        template.setProject(project);

        this.sendMail(template);
    }

    public static MultipartFile convertToMultipartFile(String fileName, byte[] content) {
        return new MockMultipartFile("file", fileName, "application/octet-stream", content);
    }

    private String getForumTitle(final NoProgrammedShareUpdate updateDto) {
        final Family family = this.familyService.getById(updateDto.getFamilyId().longValue());
        final SubFamily subFamily = this.subFamilyService.getById(updateDto.getSubFamilyId().longValue());

        final String familyName = ("es".equalsIgnoreCase(request.getLocale().getLanguage()) ? family.getNameES() : family.getNameFR())
                + (StringUtils.isNoneBlank(family.getBrand()) ? " " + family.getBrand() : "")
                + (StringUtils.isNoneBlank(family.getModel()) ? " " + family.getModel() : "")
                + (StringUtils.isNoneBlank(family.getEnrollment()) ? " " + family.getEnrollment() : "");

        return StringUtils.joinWith(" ", String.valueOf(updateDto.getId()), Utiles.getDateFormatted(updateDto.getStartDate(), "yyMMdd"),
                familyName, "es".equals(request.getLocale().getLanguage()) ? subFamily.getNameES() : subFamily.getNameFR());
    }

    private void sendMail(final OpenNoProgrammedShareMailTemplateDto template) {
        this.smtpService.openNoProgrammedShareSendMail(template);

        if (CollectionUtils.isNotEmpty(template.getProject().getResponsables())) {
            template.getProject().getResponsables().forEach(responsible -> {
                template.setEmail(responsible.getEmail());
                template.setUser(responsible);
                smtpService.openNoProgrammedShareSendMail(template);
            });
        }
    }

    private void sendMailFinal(final NoProgrammedShareDto noProgrammedShare) {
        final Supplier<RuntimeException> userNotFound = () -> new UserByIdNotFoundException(noProgrammedShare.getUserId());
        final User firstTechnical = Optional.ofNullable(this.userService.getUserById(noProgrammedShare.getUserId().longValue()))
                .orElseThrow(userNotFound);

        final Supplier<RuntimeException> projectNotFound = () -> new ProjectByIdNotFoundException(noProgrammedShare.getProjectId());
        final Project project = Optional.ofNullable(this.projectService.getProjectById(noProgrammedShare.getProjectId().longValue()))
                .orElseThrow(projectNotFound);

        final CloseNoProgrammedShareMailTemplateDto dto = new CloseNoProgrammedShareMailTemplateDto();
        dto.setLocale(request.getLocale());
        dto.setEmail(firstTechnical.getEmail());
        dto.setNoProgrammedShare(noProgrammedShare);
        dto.setUser(firstTechnical);
        dto.setProject(project);

        this.smtpService.closeNoProgrammedShareSendMail(dto);

        if (project.getResponsables() != null && !project.getResponsables().isEmpty()) {
            for (final User responsible : project.getResponsables()) {
                dto.setEmail(responsible.getEmail());
                dto.setUser(responsible);
                this.smtpService.closeNoProgrammedShareSendMail(dto);
            }
        }
    }
}
