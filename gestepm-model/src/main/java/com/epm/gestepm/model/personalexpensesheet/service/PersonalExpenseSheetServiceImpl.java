package com.epm.gestepm.model.personalexpensesheet.service;

import com.epm.gestepm.lib.audit.AuditProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.lib.user.UserProvider;
import com.epm.gestepm.model.personalexpensesheet.dao.PersonalExpenseSheetDao;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheet;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheetStatusEnum;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.creator.PersonalExpenseSheetCreate;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.deleter.PersonalExpenseSheetDelete;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.filter.PersonalExpenseSheetFilter;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.finder.PersonalExpenseSheetByIdFinder;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.updater.PersonalExpenseSheetUpdate;
import com.epm.gestepm.model.personalexpensesheet.service.mapper.*;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.OpenPersonalExpenseSheetMailTemplateDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetStatusEnumDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.creator.PersonalExpenseSheetCreateDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.deleter.PersonalExpenseSheetDeleteDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.filter.PersonalExpenseSheetFilterDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.finder.PersonalExpenseSheetByIdFinderDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.updater.PersonalExpenseSheetUpdateDto;
import com.epm.gestepm.modelapi.personalexpensesheet.exception.PersonalExpenseSheetForbiddenException;
import com.epm.gestepm.modelapi.personalexpensesheet.exception.PersonalExpenseSheetNotFoundException;
import com.epm.gestepm.modelapi.personalexpensesheet.service.PersonalExpenseSheetService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final AuditProvider auditProvider;

    private final HttpServletRequest request;

    private final PersonalExpenseSheetDao personalExpenseSheetDao;

    private final ProjectService projectService;

    private final SMTPService smtpService;

    private final UserService userService;

    public PersonalExpenseSheetServiceImpl(AuditProvider auditProvider, HttpServletRequest request, PersonalExpenseSheetDao personalExpenseSheetDao, ProjectService projectService, SMTPService smtpService, UserService userService) {
        this.auditProvider = auditProvider;
        this.request = request;
        this.personalExpenseSheetDao = personalExpenseSheetDao;
        this.projectService = projectService;
        this.smtpService = smtpService;
        this.userService = userService;
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

        this.auditProvider.auditCreate(create);

        final PersonalExpenseSheet personalExpenseSheet = this.personalExpenseSheetDao.create(create);
        return getMapper(MapPESToPersonalExpenseSheetDto.class).from(personalExpenseSheet);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PES, action = "Update personal expense sheet")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating personal expense sheet",
            msgOut = "Personal expense sheet updated OK",
            errorMsg = "Failed to update personal expense sheet")
    public PersonalExpenseSheetDto update(PersonalExpenseSheetUpdateDto updateDto) {

        final User currentUser = Utiles.getCurrentUser();

        this.checkUserPermissions(updateDto.getId(), currentUser, updateDto.getStatus());

        final PersonalExpenseSheetByIdFinderDto finderDto = new PersonalExpenseSheetByIdFinderDto();
        finderDto.setId(updateDto.getId());

        final PersonalExpenseSheetDto sheet = findOrNotFound(finderDto);

        final PersonalExpenseSheetUpdate update = getMapper(MapPESToPersonalExpenseSheetUpdate.class).from(updateDto,
                getMapper(MapPESToPersonalExpenseSheetUpdate.class).from(sheet));

        if (PersonalExpenseSheetStatusEnum.APPROVED.equals(update.getStatus())) {
            this.auditProvider.auditApprove(update);
        } else if (PersonalExpenseSheetStatusEnum.PAID.equals(update.getStatus())) {
            this.auditProvider.auditPaid(update);
        } else if (PersonalExpenseSheetStatusEnum.REJECTED.equals(update.getStatus())) {
            this.auditProvider.auditDischarge(update);
        }

        final PersonalExpenseSheet updated = this.personalExpenseSheetDao.update(update);
        final PersonalExpenseSheetDto response = getMapper(MapPESToPersonalExpenseSheetDto.class).from(updated);

        this.sendMail(response);

        return response;
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

    private void sendMail(final PersonalExpenseSheetDto personalExpenseSheet) {
        final Project project = this.projectService.getProjectById(personalExpenseSheet.getProjectId().longValue());
        final List<User> usersToNotify = this.determineUsersToNotify(personalExpenseSheet, project);

        final OpenPersonalExpenseSheetMailTemplateDto template = this.buildMailTemplate(personalExpenseSheet, project);

        usersToNotify.stream()
                .filter(userToNotify -> userToNotify.getState() == 0)
                .forEach(userToNotify -> this.sendMailToUser(template, userToNotify));
    }

    private List<User> determineUsersToNotify(final PersonalExpenseSheetDto personalExpenseSheet, final Project project) {
        final List<User> usersToNotify = new ArrayList<>();

        if (PersonalExpenseSheetStatusEnumDto.PENDING.equals(personalExpenseSheet.getStatus()) || PersonalExpenseSheetStatusEnumDto.APPROVED.equals(personalExpenseSheet.getStatus())) {
            usersToNotify.addAll(project.getBossUsers());
        } else if (PersonalExpenseSheetStatusEnumDto.PAID.equals(personalExpenseSheet.getStatus()) || PersonalExpenseSheetStatusEnumDto.REJECTED.equals(personalExpenseSheet.getStatus())) {
            usersToNotify.add(Utiles.getCurrentUser());
        }

        return usersToNotify;
    }

    private OpenPersonalExpenseSheetMailTemplateDto buildMailTemplate(final PersonalExpenseSheetDto personalExpenseSheet,
                                                                      final Project project) {
        final OpenPersonalExpenseSheetMailTemplateDto template = new OpenPersonalExpenseSheetMailTemplateDto();
        template.setLocale(request.getLocale());
        template.setPersonalExpenseSheetDto(personalExpenseSheet);
        template.setProject(project);
        template.setUser(Utiles.getCurrentUser());

        this.selectTemplateName(personalExpenseSheet, template);

        return template;
    }

    private void selectTemplateName(final PersonalExpenseSheetDto personalExpenseSheet, final OpenPersonalExpenseSheetMailTemplateDto template) {
        final String language = request.getLocale().getLanguage();

        if (PersonalExpenseSheetStatusEnumDto.PENDING.equals(personalExpenseSheet.getStatus())) {
            template.setTemplate("expense_user_mail_template_" + language + ".html");
            template.setSubject("smtp.mail.expense.user.subject");
        } else if (PersonalExpenseSheetStatusEnumDto.APPROVED.equals(personalExpenseSheet.getStatus())) {
            template.setTemplate("expense_team_leader_mail_template_" + language + ".html");
            template.setSubject("smtp.mail.expense.team.leader.subject");
        } else if (PersonalExpenseSheetStatusEnumDto.PAID.equals(personalExpenseSheet.getStatus())) {
            template.setTemplate("expense_rrhh_mail_template_" + language + ".html");
            template.setSubject("smtp.mail.expense.rrhh.subject");
        } else if (PersonalExpenseSheetStatusEnumDto.REJECTED.equals(personalExpenseSheet.getStatus())) {
            template.setTemplate("expense_decline_mail_template_" + language + ".html");
            template.setSubject("smtp.mail.expense.decline.subject");
        }
    }

    private void sendMailToUser(final OpenPersonalExpenseSheetMailTemplateDto template, final User user) {
        template.setEmail(user.getEmail());
        smtpService.sendPersonalExpenseSheetSendMail(template);
    }

    private void checkUserPermissions(final Integer personalExpenseSheetId, final User user, final PersonalExpenseSheetStatusEnumDto status) {
        final Long roleId = user.getRole().getId();
        final List<Boolean> conditions = Arrays.asList(
                PersonalExpenseSheetStatusEnumDto.APPROVED.equals(status) && roleId < Constants.ROLE_PL_ID,
                PersonalExpenseSheetStatusEnumDto.PAID.equals(status) && roleId < Constants.ROLE_ADMINISTRATION_ID,
                PersonalExpenseSheetStatusEnumDto.REJECTED.equals(status) && roleId < Constants.ROLE_PL_ID
        );

        if (conditions.stream().anyMatch(condition -> condition)) {
            throw new PersonalExpenseSheetForbiddenException(personalExpenseSheetId);
        }
    }
}
