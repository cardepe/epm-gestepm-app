package com.epm.gestepm.model.common.utils.smtp;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.OpenPersonalExpenseSheetMailTemplateDto;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;
import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class SMTPServiceImpl implements SMTPService {
	
	private static final Log log = LogFactory.getLog(SMTPServiceImpl.class);

	private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	@Value("${smtp.mail.from}")
	private String smtpMailFrom;
	
	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private MessageSource messageSource;
	
	@Async
	public void sendCreateHolidaysRRHHMail(String to, User user, String holidays, Locale locale) {
		
		log.info("Preparando la plantilla de correo: holidays_create_mail_template_" + locale.getLanguage() + ".html");
		
		String subject = messageSource.getMessage("smtp.mail.holidays.subject", new Object[] { user.getName() + " " + user.getSurnames() }, locale);
		
		Map<String, String> params = new HashMap<>();
		params.put("username", user.getName() + " " + user.getSurnames());
		params.put("userId", user.getId().toString());
		params.put("holidays", holidays);
		
		loadTemplateAndSendMail(smtpMailFrom, to, subject, "holidays_create_mail_template_" + locale.getLanguage() + ".html", params);
	}

	@Async
	public void sendDeleteHolidaysRRHHMail(String to, User user, String holidays, Locale locale) {

		log.info("Preparando la plantilla de correo: holidays_delete_mail_template_" + locale.getLanguage() + ".html");

		String subject = messageSource.getMessage("smtp.mail.holidays.subject", new Object[] { user.getName() + " " + user.getSurnames() }, locale);

		Map<String, String> params = new HashMap<>();
		params.put("username", user.getName() + " " + user.getSurnames());
		params.put("userId", user.getId().toString());
		params.put("holidays", holidays);

		loadTemplateAndSendMail(smtpMailFrom, to, subject, "holidays_delete_mail_template_" + locale.getLanguage() + ".html", params);
	}

	@Async
	public void sendPersonalExpenseSheetSendMail(final OpenPersonalExpenseSheetMailTemplateDto dto) {
		
		log.info("Preparando la plantilla de correo: " + dto.getTemplate());
		
		final String subject = messageSource.getMessage(dto.getSubject(), new Object[] { }, dto.getLocale());
		
		final Map<String, String> params = new HashMap<>();
		params.put("id", dto.getPersonalExpenseSheetDto().getId().toString());
		params.put("projectId", dto.getProject().getId().toString());
		params.put("username", dto.getUser().getFullName());
		params.put("projectName", dto.getProject().getName());
		params.put("noticeDate", Utiles.transform(dto.getPersonalExpenseSheetDto().getCreatedAt(), DATE_TIME_FORMAT));
		params.put("description", dto.getPersonalExpenseSheetDto().getDescription());

		if (StringUtils.isNoneBlank(dto.getPersonalExpenseSheetDto().getObservations())) {
			final String observations = messageSource.getMessage("smtp.mail.expense.decline.observation",
					new Object[] { dto.getPersonalExpenseSheetDto().getObservations() }, dto.getLocale());
			params.put("observations", observations);
		}


		loadTemplateAndSendMail(smtpMailFrom, dto.getEmail(), subject, dto.getTemplate(), params);
	}

	@Async
	public void sendSigningManualMail(String to, UserManualSigning userManualSigning, Locale locale) {

		log.info("Preparando la plantilla de correo: signing_manual_mail_template_" + locale.getLanguage() + ".html");

		String subject = messageSource.getMessage("smtp.mail.signing.manual.subject", new Object[] { }, locale);

		Map<String, String> params = new HashMap<>();
		params.put("username", userManualSigning.getUser().getName() + " " + userManualSigning.getUser().getSurnames());
		params.put("userId", userManualSigning.getUser().getId().toString());
		params.put("manualSigningType", userManualSigning.getManualSigningType().getName());
		params.put("startDate", Utiles.transform(userManualSigning.getStartDate(), DATE_TIME_FORMAT));
		params.put("endDate", Utiles.transform(userManualSigning.getEndDate(), DATE_TIME_FORMAT));

		loadTemplateAndSendMail(smtpMailFrom, to, subject, "signing_manual_mail_template_" + locale.getLanguage() + ".html", params);
	}
	
	@Async
	public void sendHolidayDeclineMail(String to, User user, UserHoliday userHoliday, Locale locale) {
		
		log.info("Preparando la plantilla de correo: holiday_decline_mail_template_" + locale.getLanguage() + ".html");
		
		String subject = messageSource.getMessage("smtp.mail.holiday.decline.subject", new Object[] { }, locale);

		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(userHoliday.getId()));
		params.put("date", Utiles.getDateFormattedESP(userHoliday.getDate()));
		params.put("username", user.getName() + " " + user.getSurnames());
		
		if (StringUtils.isNoneBlank(userHoliday.getObservations())) {
			
			String observations = messageSource.getMessage("smtp.mail.holiday.decline.observation", new Object[] { userHoliday.getObservations() }, locale);

			params.put("observations", observations);
		}
		
		loadTemplateAndSendMail(smtpMailFrom, to, subject, "holiday_decline_mail_template_" + locale.getLanguage() + ".html", params);
	}
	
	@Async
	public void sendCorrectiveTeamLeaderMail(String to, User user, ExpenseCorrective corrective, Project project, Locale locale) {
		
		log.info("Preparando la plantilla de correo: expense_corrective_mail_template_" + locale.getLanguage() + ".html");
		
		String subject = messageSource.getMessage("smtp.mail.expense.corrective.subject", new Object[] { }, locale);

		Map<String, String> params = new HashMap<>();
		params.put("projectName", project.getName());
		params.put("cost", String.valueOf(corrective.getCost()));
		params.put("description", corrective.getDescription());
		params.put("date", Utiles.getDateFormattedESP(corrective.getCreationDate()));

		loadTemplateAndSendMail(smtpMailFrom, to, subject, "expense_corrective_mail_template_" + locale.getLanguage() + ".html", params);
	}

	private void sendHtmlMail(String from, String to, String subject, String content) {

		try {
			
			if (StringUtils.isEmpty(to)) {
				log.error("El email del destinatario es vacío o está mal formulado.");
				return;
			}
			
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			
			helper.setFrom(from);
			helper.setTo(to); 
			helper.setSubject(subject); 
			helper.setText(content, true);
	        emailSender.send(mimeMessage);
	        
	        log.info("Correo electronico enviado al correo " + to);
	        
		} catch (Exception e) {
			log.error("Error al enviar un correo electronico HTML para " + to, e);
		}
	}

	private void loadTemplateAndSendMail(String from, String to, String subject, String templateName, Map<String, String> params) {
		
		try {

			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/mail/" + templateName);
			String templateContent = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());

			for (Map.Entry<String, String> entry : params.entrySet()) {

				if (entry.getValue() == null) {
					continue;
				}

				templateContent = templateContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
			}
			
			sendHtmlMail(from, to, subject, templateContent);
			
		} catch (Exception e) {
			log.error("Error al cargar el template " + templateName + " y enviar el correo para " + to, e);
		}
	}
}
