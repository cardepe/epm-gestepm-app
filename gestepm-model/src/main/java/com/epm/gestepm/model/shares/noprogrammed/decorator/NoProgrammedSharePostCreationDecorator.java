package com.epm.gestepm.model.shares.noprogrammed.decorator;

import com.epm.gestepm.emailapi.dto.emailgroup.CloseNoProgrammedShareGroup;
import com.epm.gestepm.emailapi.dto.emailgroup.OpenNoProgrammedShareGroup;
import com.epm.gestepm.emailapi.service.EmailService;
import com.epm.gestepm.forum.model.api.service.TopicService;
import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.model.shares.noprogrammed.dao.NoProgrammedShareDao;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareFileCreate;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.NoProgrammedShareUpdate;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.MapNPSToNoProgrammedShareDto;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.MapNPSToNoProgrammedShareUpdate;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.MapNPSToNoProgrammedShareUpdateDto;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.CloseNoProgrammedShareMailTemplateDto;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.OpenNoProgrammedShareMailTemplateDto;
import com.epm.gestepm.modelapi.family.dto.Family;
import com.epm.gestepm.modelapi.family.service.FamilyService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.exception.ProjectByIdNotFoundException;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.UserByIdNotFoundException;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.itextpdf.xmp.impl.Base64;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mapstruct.factory.Mappers.getMapper;

@Data
@Component
@Validated
public class NoProgrammedSharePostCreationDecorator {

    private final EmailService emailService;

    private final FamilyService familyService;

    private final HttpServletRequest request;

    private final NoProgrammedShareDao noProgrammedShareDao;

    private final ProjectService projectService;

    private final SMTPService smtpService;

    private final SubFamilyService subFamilyService;

    private final TopicService topicService;

    private final UserService userService;

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    public void createForumEntryAndUpdate(final NoProgrammedShareDto noProgrammedShare, final Set<NoProgrammedShareFileCreate> files) {
        final Project project = this.projectService.getProjectById(Long.valueOf(noProgrammedShare.getProjectId()));
        final Long forumId = project.getForumId();
        final String forumTitle = this.getForumTitle(noProgrammedShare);
        final String ip = request.getLocalAddr();
        final List<MultipartFile> multipartFiles = CollectionUtils.isNotEmpty(files)
                ? files.stream()
                .map(file -> convertToMultipartFile(file.getName(), Base64.decode(file.getContent()).getBytes()))
                .collect(Collectors.toList())
                : new ArrayList<>();

        final Locale locale = new Locale(this.localeProvider.getLocale().orElse("es"));
        final User user = this.userService.getUserById(Long.valueOf(noProgrammedShare.getUserId()));

        topicService.create(forumTitle, noProgrammedShare.getDescription(), forumId, ip, user.getUsername(), multipartFiles)
                .whenComplete((topic, throwable) -> {
                    final NoProgrammedShareDto dto = throwable == null
                            ? this.updateWithForumInfo(noProgrammedShare, topic.getId().intValue(), forumTitle)
                            : noProgrammedShare;
                    this.sendOpenEmail(dto, user, project, locale);
                });
    }

    private static MultipartFile convertToMultipartFile(String fileName, byte[] content) {
        return new MockMultipartFile("file", fileName, "application/octet-stream", content);
    }

    private NoProgrammedShareDto updateWithForumInfo(final NoProgrammedShareDto noProgrammedShare, final Integer topicId, final String forumTitle) {
        final NoProgrammedShareUpdate update = getMapper(MapNPSToNoProgrammedShareUpdate.class).from(noProgrammedShare);
        update.setId(noProgrammedShare.getId());
        update.setTopicId(topicId);
        update.setForumTitle(forumTitle);

        return getMapper(MapNPSToNoProgrammedShareDto.class).from(this.noProgrammedShareDao.update(update));
    }

    private String getForumTitle(final NoProgrammedShareDto noProgrammedShare) {
        final String language = this.localeProvider.getLocale().orElse("es");
        final Family family = this.familyService.getById(noProgrammedShare.getFamilyId().longValue());
        final SubFamily subFamily = this.subFamilyService.getById(noProgrammedShare.getSubFamilyId().longValue());

        final String familyName = ("es".equalsIgnoreCase(language) ? family.getNameES() : family.getNameFR())
                + (StringUtils.isNoneBlank(family.getBrand()) ? " " + family.getBrand() : "")
                + (StringUtils.isNoneBlank(family.getModel()) ? " " + family.getModel() : "")
                + (StringUtils.isNoneBlank(family.getEnrollment()) ? " " + family.getEnrollment() : "");

        return StringUtils.joinWith(" ", String.valueOf(noProgrammedShare.getId()), Utiles.getDateFormatted(noProgrammedShare.getStartDate(), "yyMMdd"),
                familyName, "es".equals(language) ? subFamily.getNameES() : subFamily.getNameFR());
    }

    private void sendOpenEmail(final NoProgrammedShareDto noProgrammedShare, final User createdBy, final Project project, final Locale locale) {
        final String subject = this.messageSource.getMessage("email.noprogrammedshare.open.subject", new Object[] {
                noProgrammedShare.getForumTitle(),
                project.getName()
        }, locale);

        final Set<String> emails = new HashSet<>();
        emails.add(createdBy.getEmail());
        emails.addAll(project.getResponsables().stream().map(User::getEmail).collect(Collectors.toSet()));

        final OpenNoProgrammedShareGroup emailGroup = new OpenNoProgrammedShareGroup();
        emailGroup.setEmails(new ArrayList<>(emails));
        emailGroup.setSubject(subject);
        emailGroup.setLocale(locale);
        emailGroup.setFullName(createdBy.getFullName());
        emailGroup.setNoProgrammedShareId(noProgrammedShare.getId());
        emailGroup.setProjectName(project.getName());
        emailGroup.setCreatedAt(noProgrammedShare.getStartDate());
        emailGroup.setDescription(noProgrammedShare.getDescription());
        emailGroup.setForumTitle(noProgrammedShare.getForumTitle());
        emailGroup.setForumUrl(ObjectUtils.allNotNull(project.getForumId(), noProgrammedShare.getTopicId())
                ? "viewtopic.php?f=" + project.getForumId() + "&t=" + noProgrammedShare.getTopicId().toString()
                : "-");

        this.emailService.sendEmail(emailGroup);
    }

    public void sendCloseEmail(final NoProgrammedShareDto noProgrammedShare) {
        final User user = Utiles.getCurrentUser();

        final Supplier<RuntimeException> projectNotFound = () -> new ProjectByIdNotFoundException(noProgrammedShare.getProjectId());
        final Project project = Optional.ofNullable(this.projectService.getProjectById(noProgrammedShare.getProjectId().longValue()))
                .orElseThrow(projectNotFound);

        final Locale locale = new Locale(this.localeProvider.getLocale().orElse("es"));
        final String subject = this.messageSource.getMessage("email.noprogrammedshare.close.subject", new Object[] {
                noProgrammedShare.getForumTitle(),
                project.getName()
        }, locale);

        final Set<String> emails = new HashSet<>();
        emails.add(user.getEmail());
        emails.addAll(project.getResponsables().stream().map(User::getEmail).collect(Collectors.toSet()));

        final CloseNoProgrammedShareGroup emailGroup = new CloseNoProgrammedShareGroup();
        emailGroup.setEmails(new ArrayList<>(emails));
        emailGroup.setSubject(subject);
        emailGroup.setLocale(locale);
        emailGroup.setFullName(user.getFullName());
        emailGroup.setNoProgrammedShareId(noProgrammedShare.getId());
        emailGroup.setProjectName(project.getName());
        emailGroup.setCreatedAt(noProgrammedShare.getStartDate());
        emailGroup.setClosedAt(noProgrammedShare.getEndDate());
        emailGroup.setForumTitle(noProgrammedShare.getForumTitle());
        emailGroup.setForumUrl(ObjectUtils.allNotNull(project.getForumId(), noProgrammedShare.getTopicId())
                ? "viewtopic.php?f=" + project.getForumId() + "&t=" + noProgrammedShare.getTopicId().toString()
                : "-");

        this.emailService.sendEmail(emailGroup);
    }
}
