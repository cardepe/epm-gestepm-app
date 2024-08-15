package com.epm.gestepm.scheduled;

import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.user.dto.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class ShareScheduled {

    private final ProjectService projectService;

    private final SMTPService smtpService;

    public ShareScheduled(ProjectService projectService, SMTPService smtpService) {
        this.projectService = projectService;
        this.smtpService = smtpService;
    }

    // @Scheduled(cron = "0 0 12 * * MON")
    public void sendWeeklySharesResumeToTeamLeaders() {

        final List<Project> projects = this.projectService.getAllProjects();

        for (Project project : projects) {

            final List<User> teamLeaders = project.getBossUsers();

            teamLeaders.forEach(tl -> this.smtpService.sendProjectWeeklySharesResume(tl.getEmail(), project, new Locale(tl.getActivityCenter().getCountry().getTag())));
        }
    }

}
