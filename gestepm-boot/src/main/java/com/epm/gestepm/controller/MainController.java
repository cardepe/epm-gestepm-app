package com.epm.gestepm.controller;

import com.epm.gestepm.modelapi.common.utils.smtp.EmailService;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.EmailTemplateDto;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.OpenPersonalExpenseSheetMailTemplateDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetDto;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;

@Controller
public class MainController {

	@Value("${smtp.mail.from}")
	private String smtpMailFrom;

	private final EmailService emailService;

	private final HttpServletRequest request;

	private final ProjectService projectService;

	private final SMTPService smtpService;

	private final UserService userService;

    public MainController(EmailService emailService, HttpServletRequest request, ProjectService projectService, SMTPService smtpService, UserService userService) {
        this.emailService = emailService;
        this.request = request;
        this.projectService = projectService;
        this.smtpService = smtpService;
        this.userService = userService;
    }

    @GetMapping("/")
	public String loginMessage() {
		return "redirect:/signing/personal"; 
	}

	@GetMapping("/v1/test")
	public void toTest() {

		final Project project = this.projectService.getProjectById(301L);
		final User user = this.userService.getUserById(1L);

		final PersonalExpenseSheetDto personalExpenseSheetDto = new PersonalExpenseSheetDto();
		personalExpenseSheetDto.setStartDate(LocalDateTime.now());
		personalExpenseSheetDto.setDescription("This is a test");

		final OpenPersonalExpenseSheetMailTemplateDto template = new OpenPersonalExpenseSheetMailTemplateDto();
		template.setLocale(request.getLocale());
		template.setPersonalExpenseSheetDto(personalExpenseSheetDto);
		template.setProject(project);
		template.setEmail(user.getEmail());
		template.setUser(user);

		final EmailTemplateDto emailTemplate = new EmailTemplateDto();
		emailTemplate.setFrom(smtpMailFrom);
		emailTemplate.setTo(user.getEmail());
		emailTemplate.setSubject("Cabecera de pruebas");
		emailTemplate.setParams(new HashMap<>());

		this.emailService.process(emailTemplate);
	}

	@GetMapping("/templates/email")
	public String emailTemplate(final Model model) {
		return "emailTemplate";
	}
}
