package com.epm.gestepm.model.shares.noprogrammed.decorator;

import com.epm.gestepm.forum.model.api.service.TopicService;
import com.epm.gestepm.model.shares.noprogrammed.dao.NoProgrammedShareDao;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.NoProgrammedShareUpdate;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.MapNPSToNoProgrammedShareUpdateDto;
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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.mapstruct.factory.Mappers.getMapper;

@Component
@Validated
public class NoProgrammedSharePostCreationDecorator {

    private final FamilyService familyService;

    private final HttpServletRequest request;

    private final NoProgrammedShareDao noProgrammedShareDao;

    private final ProjectService projectService;

    private final SMTPService smtpService;

    private final SubFamilyService subFamilyService;

    private final TopicService topicService;

    private final UserService userService;

    public NoProgrammedSharePostCreationDecorator(FamilyService familyService, HttpServletRequest request, NoProgrammedShareDao noProgrammedShareDao, ProjectService projectService, SMTPService smtpService, SubFamilyService subFamilyService, TopicService topicService, UserService userService) {
        this.familyService = familyService;
        this.request = request;
        this.noProgrammedShareDao = noProgrammedShareDao;
        this.projectService = projectService;
        this.smtpService = smtpService;
        this.subFamilyService = subFamilyService;
        this.topicService = topicService;
        this.userService = userService;
    }

    public void createForumEntryAndUpdate(final NoProgrammedShareDto noProgrammedShare, NoProgrammedShareUpdate update) {
        final Project project = this.projectService.getProjectById(Long.valueOf(noProgrammedShare.getProjectId()));
        final Long forumId = project.getForumId();
        final String forumTitle = this.getForumTitle(update);
        final String ip = request.getLocalAddr();
        final List<MultipartFile> files = CollectionUtils.isNotEmpty(update.getFiles())
                ? update.getFiles().stream()
                .map(file -> convertToMultipartFile(file.getName(), Base64.decode(file.getContent()).getBytes()))
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
        template.setNoProgrammedShare(dto);
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
                smtpService.openNoProgrammedShareSendMail(template);
            });
        }
    }

    public void sendMailFinal(final NoProgrammedShareDto noProgrammedShare) {
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
                this.smtpService.closeNoProgrammedShareSendMail(dto);
            }
        }
    }
}
