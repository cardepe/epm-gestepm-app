package com.epm.gestepm.model.shares.construction.service;

import com.epm.gestepm.emailapi.dto.Attachment;
import com.epm.gestepm.emailapi.dto.emailgroup.CloseConstructionShareGroup;
import com.epm.gestepm.emailapi.service.EmailService;
import com.epm.gestepm.lib.audit.AuditProvider;
import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.common.checker.ShareDateChecker;
import com.epm.gestepm.model.shares.construction.dao.ConstructionShareDao;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShare;
import com.epm.gestepm.model.shares.construction.dao.entity.filter.ConstructionShareFilter;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSToConstructionShareDto;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSToConstructionShareFilter;
import com.epm.gestepm.model.shares.construction.dao.entity.creator.ConstructionShareCreate;
import com.epm.gestepm.model.shares.construction.dao.entity.deleter.ConstructionShareDelete;
import com.epm.gestepm.model.shares.construction.dao.entity.finder.ConstructionShareByIdFinder;
import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareUpdate;
import com.epm.gestepm.model.shares.construction.service.mapper.*;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.exception.ProjectByIdNotFoundException;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFilterDto;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareExportService;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareService;
import com.epm.gestepm.modelapi.shares.construction.dto.creator.ConstructionShareCreateDto;
import com.epm.gestepm.modelapi.shares.construction.dto.deleter.ConstructionShareDeleteDto;
import com.epm.gestepm.modelapi.shares.construction.dto.finder.ConstructionShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareUpdateDto;
import com.epm.gestepm.modelapi.shares.construction.exception.ConstructionShareNotFoundException;
import com.epm.gestepm.modelapi.user.dto.User;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_EDIT_CS;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_READ_CS;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service
@AllArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class ConstructionShareServiceImpl implements ConstructionShareService {

    private final AuditProvider auditProvider;

    private final ConstructionShareDao constructionShareDao;

    private final ConstructionShareExportService constructionShareExportService;

    private final EmailService emailService;

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    private final ProjectService projectService;

    private final ShareDateChecker shareDateChecker;

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "List construction shares")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing construction shares",
            msgOut = "Listing construction shares OK",
            errorMsg = "Failed to list construction shares")
    public List<ConstructionShareDto> list(ConstructionShareFilterDto filterDto) {
        final ConstructionShareFilter filter = getMapper(MapCSToConstructionShareFilter.class).from(filterDto);

        final List<ConstructionShare> list = this.constructionShareDao.list(filter);

        return getMapper(MapCSToConstructionShareDto.class).from(list);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing construction shares",
            msgOut = "Listing construction shares OK",
            errorMsg = "Failed to list construction shares")
    public Page<ConstructionShareDto> list(ConstructionShareFilterDto filterDto, Long offset, Long limit) {

        final ConstructionShareFilter filter = getMapper(MapCSToConstructionShareFilter.class).from(filterDto);

        final Page<ConstructionShare> page = this.constructionShareDao.list(filter, offset, limit);

        return getMapper(MapCSToConstructionShareDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "Find construction share by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding construction share by ID, result can be empty",
            msgOut = "Found construction share by ID",
            errorMsg = "Failed to find construction share by ID")
    public Optional<ConstructionShareDto> find(final ConstructionShareByIdFinderDto finderDto) {
        final ConstructionShareByIdFinder finder = getMapper(MapCSToConstructionShareByIdFinder.class).from(finderDto);

        final Optional<ConstructionShare> found = this.constructionShareDao.find(finder);

        return found.map(getMapper(MapCSToConstructionShareDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "Find construction share by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding construction share by ID, result is expected or will fail",
            msgOut = "Found construction share by ID",
            errorMsg = "Construction share by ID not found")
    public ConstructionShareDto findOrNotFound(final ConstructionShareByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new ConstructionShareNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_CS, action = "Create new construction share")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new construction share",
            msgOut = "New construction share created OK",
            errorMsg = "Failed to create new construction share")
    public ConstructionShareDto create(ConstructionShareCreateDto createDto) {
        final ConstructionShareCreate create = getMapper(MapCSToConstructionShareCreate.class).from(createDto);
        create.setStartDate(LocalDateTime.now());

        this.auditProvider.auditCreate(create);

        final ConstructionShare result = this.constructionShareDao.create(create);

        return getMapper(MapCSToConstructionShareDto.class).from(result);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_CS, action = "Update construction share")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating construction share",
            msgOut = "Construction share updated OK",
            errorMsg = "Failed to update construction share")
    public ConstructionShareDto update(final ConstructionShareUpdateDto updateDto) {
        final ConstructionShareByIdFinderDto finderDto = new ConstructionShareByIdFinderDto(updateDto.getId());

        final ConstructionShareDto constructionShareDto = findOrNotFound(finderDto);

        final ConstructionShareUpdate update = getMapper(MapCSToConstructionShareUpdate.class).from(updateDto,
                getMapper(MapCSToConstructionShareUpdate.class).from(constructionShareDto));

        final LocalDateTime endDate = this.shareDateChecker.checkMaxHours(update.getStartDate(), update.getEndDate() != null
                ? update.getEndDate()
                : LocalDateTime.now());
        update.setEndDate(endDate);

        this.shareDateChecker.checkStartBeforeEndDate(update.getStartDate(), update.getEndDate());

        if (update.getClosedAt() == null) {
            this.auditProvider.auditClose(update);
        }

        final ConstructionShare updated = this.constructionShareDao.update(update);
        final ConstructionShareDto result = getMapper(MapCSToConstructionShareDto.class).from(updated);

        this.sendMail(result, updateDto.getNotify());

        return result;
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_CS, action = "Delete construction share")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting construction share",
            msgOut = "Construction share deleted OK",
            errorMsg = "Failed to delete construction share")
    public void delete(ConstructionShareDeleteDto deleteDto) {

        final ConstructionShareByIdFinderDto finderDto = new ConstructionShareByIdFinderDto(deleteDto.getId());

        findOrNotFound(finderDto);

        final ConstructionShareDelete delete = getMapper(MapCSToConstructionShareDelete.class).from(deleteDto);

        this.constructionShareDao.delete(delete);
    }

    private void sendMail(final ConstructionShareDto constructionShare, final Boolean notify) {
        final byte[] pdf = this.constructionShareExportService.generate(constructionShare);
        final String base64PDF = Base64.getEncoder().encodeToString(pdf);

        final User user = Utiles.getCurrentUser();

        final Supplier<RuntimeException> projectNotFound = () -> new ProjectByIdNotFoundException(constructionShare.getProjectId());
        final Project project = Optional.ofNullable(this.projectService.getProjectById(constructionShare.getProjectId().longValue()))
                .orElseThrow(projectNotFound);

        final Locale locale = new Locale(this.localeProvider.getLocale().orElse("es"));
        final String fileName = this.messageSource.getMessage("shares.construction.pdf.name", new Object[]{
                constructionShare.getId().toString(),
                Utiles.transform(constructionShare.getStartDate(), "dd-MM-yyyy")
        }, locale) + ".pdf";

        final Attachment attachment = new Attachment();
        attachment.setFileName(fileName);
        attachment.setFileData(base64PDF);
        attachment.setContentType("application/pdf");

        final String subject = messageSource.getMessage("email.constructionshare.close.subject", new Object[]{
                constructionShare.getId()
        }, locale);

        final Set<String> emails = new HashSet<>();
        emails.add(user.getEmail());
        emails.addAll(project.getResponsables().stream().map(User::getEmail).collect(Collectors.toSet()));

        if (BooleanUtils.isTrue(notify) && project.getCustomer() != null) {
            final String mainEmail = project.getCustomer().getMainEmail();
            final String secondaryEmail = project.getCustomer().getSecondaryEmail();

            if (StringUtils.isNoneBlank(mainEmail)) {
                emails.add(mainEmail);
            }

            if (StringUtils.isNoneBlank(secondaryEmail)) {
                emails.add(secondaryEmail);
            }
        }

        final CloseConstructionShareGroup emailGroup = new CloseConstructionShareGroup();
        emailGroup.setEmails(new ArrayList<>(emails));
        emailGroup.setSubject(subject);
        emailGroup.setLocale(locale);
        emailGroup.setConstructionShareId(constructionShare.getId());
        emailGroup.setFullName(user.getFullName());
        emailGroup.setProjectName(project.getName());
        emailGroup.setCreatedAt(constructionShare.getStartDate());
        emailGroup.setClosedAt(constructionShare.getEndDate());
        emailGroup.setAttachments(List.of(attachment));

        this.emailService.sendEmail(emailGroup);
    }
}
