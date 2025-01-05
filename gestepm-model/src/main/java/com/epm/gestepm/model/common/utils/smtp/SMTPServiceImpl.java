package com.epm.gestepm.model.common.utils.smtp;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.CloseInspectionMailTemplateDto;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.CloseNoProgrammedShareMailTemplateDto;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.OpenNoProgrammedShareMailTemplateDto;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.OpenPersonalExpenseSheetMailTemplateDto;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigning;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class SMTPServiceImpl implements SMTPService {
	
	private static final Log log = LogFactory.getLog(SMTPServiceImpl.class);

	private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	@Value("${base.url}")
	private String appUrlBase;
	
	@Value("${god.mail}")
	private String godMail;
	
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
	public void sendCloseConstructionShareMail(String to, ConstructionShare share, byte[] pdfGenerated, Locale locale) {
		
		log.info("Preparando la plantilla de correo: construction_share_close_mail_template_" + locale.getLanguage() + ".html");
		
		String subject = messageSource.getMessage("smtp.mail.construction.share.close.subject", new Object[] { share.getId().toString() }, locale);
		
		Map<String, String> params = new HashMap<>();
		params.put("id", share.getId().toString());
		params.put("username", share.getUser().getName() + " " + share.getUser().getSurnames());
		params.put("projectName", share.getProject().getName());
		params.put("startDate", Utiles.transform(share.getStartDate(), DATE_TIME_FORMAT));
		params.put("endDate", Utiles.transform(share.getEndDate(), DATE_TIME_FORMAT));

		loadPDFTemplateAndSendMail(share, "cs", pdfGenerated, smtpMailFrom, to, subject, "construction_share_close_mail_template_" + locale.getLanguage() + ".html", params, locale);
	}
	
	@Async
	public void openNoProgrammedShareSendMail(final OpenNoProgrammedShareMailTemplateDto dto) {
		final Locale locale = dto.getLocale();
		final NoProgrammedShareUpdateDto noProgrammedShare = dto.getNoProgrammedShare();
		final User user = dto.getUser();
		final Project project = dto.getProject();

		log.info("Preparando la plantilla de correo: intervention_share_open_mail_template_" + locale.getLanguage() + ".html");

		final String subject = messageSource.getMessage("smtp.mail.intervention.share.open.subject", new Object[] {
				noProgrammedShare.getForumTitle(),
				project.getName()
		}, locale);
		
		final Map<String, String> params = new HashMap<>();
		params.put("id", noProgrammedShare.getId().toString());
		params.put("username", user.getFullName());
		params.put("projectName", project.getName());
		params.put("startDate", Utiles.transform(noProgrammedShare.getStartDate(), DATE_TIME_FORMAT));
		params.put("description", noProgrammedShare.getDescription());
		params.put("idUrl", noProgrammedShare.getId().toString());
		params.put("forumUrl", project.getForumId() == null || noProgrammedShare.getTopicId() == null
				? "-" : "viewtopic.php?f=" + project.getForumId() + "&t=" + noProgrammedShare.getTopicId().toString());
		params.put("forumTitle", noProgrammedShare.getForumTitle());
		
		loadTemplateAndSendMail(smtpMailFrom, dto.getEmail(), subject, "intervention_share_open_mail_template_" + locale.getLanguage() + ".html", params);
	}
	
	@Async
	public void closeNoProgrammedShareSendMail(final CloseNoProgrammedShareMailTemplateDto dto) {
		final Locale locale = dto.getLocale();
		final NoProgrammedShareDto noProgrammedShare = dto.getNoProgrammedShare();
		final User user = dto.getUser();
		final Project project = dto.getProject();

		log.info("Preparando la plantilla de correo: intervention_share_close_mail_template_" + locale.getLanguage() + ".html");

		final String subject = messageSource.getMessage("smtp.mail.intervention.share.close.subject", new Object[] {
				noProgrammedShare.getForumTitle(),
				project.getName()
		}, locale);
		
		final Map<String, String> params = new HashMap<>();
		params.put("id", noProgrammedShare.getId().toString());
		params.put("username", user.getFullName());
		params.put("projectName", project.getName());
		params.put("startDate", Utiles.transform(noProgrammedShare.getStartDate(), DATE_TIME_FORMAT));
		params.put("endDate", Utiles.transform(noProgrammedShare.getEndDate(), DATE_TIME_FORMAT));
		params.put("idUrl", noProgrammedShare.getId().toString());
		params.put("forumUrl", "viewtopic.php?f=" + project.getForumId() + "&t=" + noProgrammedShare.getTopicId());
		params.put("forumTitle", noProgrammedShare.getForumTitle());
		
		loadTemplateAndSendMail(smtpMailFrom, dto.getEmail(), subject, "intervention_share_close_mail_template_" + locale.getLanguage() + ".html", params);
	}
	
	@Async
	public void closeInspectionSendMail(final CloseInspectionMailTemplateDto dto) {
		final Locale locale = dto.getLocale();
		final NoProgrammedShareDto noProgrammedShare = dto.getNoProgrammedShare();
		final InspectionDto inspection = dto.getInspection();
		final User user = dto.getUser();
		final Project project = dto.getProject();

		log.info("Preparando la plantilla de correo: intervention_sub_share_close_mail_template_" + locale.getLanguage() + ".html");

		final String action = this.messageSource.getMessage(dto.getInspection().getAction().toString().toLowerCase(), new Object[] {}, locale);
		final String subject = messageSource.getMessage("smtp.mail.intervention.sub.share.close.subject", new Object[] {
				inspection.getId(),
				action
		}, locale);
		
		final Map<String, String> params = new HashMap<>();
		params.put("id", inspection.getId().toString());
		params.put("username", user.getFullName());
		params.put("projectName", project.getName());
		params.put("startDate", Utiles.transform(inspection.getStartDate(), DATE_TIME_FORMAT));
		params.put("endDate", Utiles.transform(inspection.getEndDate(), DATE_TIME_FORMAT));
		params.put("subShareType", action);
		params.put("idUrl", noProgrammedShare.getId().toString());
		params.put("forumUrl", "viewtopic.php?f=" + project.getForumId() + "&t=" + noProgrammedShare.getTopicId());
		params.put("forumTitle", noProgrammedShare.getForumTitle());
		
		loadPDFTemplateAndSendMail(inspection, "is", dto.getPdf(), smtpMailFrom, dto.getEmail(), subject, "intervention_sub_share_close_mail_template_" + locale.getLanguage() + ".html", params, locale);
	}
	
	@Async
	public void sendCloseProgrammedShareMail(String to, InterventionPrShare share, byte[] pdfGenerated, Locale locale) {
		
		log.info("Preparando la plantilla de correo: programmed_share_close_mail_template_" + locale.getLanguage() + ".html");
		
		String subject = messageSource.getMessage("smtp.mail.programmed.share.close.subject", new Object[] { share.getId().toString() }, locale);
		
		Map<String, String> params = new HashMap<>();
		params.put("id", share.getId().toString());
		params.put("username", share.getUser().getName() + " " + share.getUser().getSurnames());
		params.put("projectName", share.getProject().getName());
		params.put("startDate", Utiles.transform(share.getStartDate(), DATE_TIME_FORMAT));
		params.put("endDate", Utiles.transform(share.getEndDate(), DATE_TIME_FORMAT));

		loadPDFTemplateAndSendMail(share, "ips", pdfGenerated, smtpMailFrom, to, subject, "programmed_share_close_mail_template_" + locale.getLanguage() + ".html", params, locale);
	}
	
	@Async
	public void sendCloseWorkShareMail(String to, WorkShare share, byte[] pdfGenerated, Locale locale) {
		
		log.info("Preparando la plantilla de correo: work_share_close_mail_template_" + locale.getLanguage() + ".html");
		
		String subject = messageSource.getMessage("smtp.mail.work.share.close.subject", new Object[] { share.getId().toString() }, locale);
		
		Map<String, String> params = new HashMap<>();
		params.put("id", share.getId().toString());
		params.put("username", share.getUser().getName() + " " + share.getUser().getSurnames());
		params.put("projectName", share.getProject().getName());
		params.put("startDate", Utiles.transform(share.getStartDate(), DATE_TIME_FORMAT));
		params.put("endDate", Utiles.transform(share.getEndDate(), DATE_TIME_FORMAT));

		loadPDFTemplateAndSendMail(share, "ws", pdfGenerated, smtpMailFrom, to, subject, "work_share_close_mail_template_" + locale.getLanguage() + ".html", params, locale);
	}

	@Async
	public void openPersonalExpenseSheetSendMail(final OpenPersonalExpenseSheetMailTemplateDto dto) {
		
		log.info("Preparando la plantilla de correo: expense_user_mail_template_" + dto.getLocale().getLanguage() + ".html");
		
		String subject = messageSource.getMessage("smtp.mail.expense.user.subject", new Object[] { }, dto.getLocale());
		
		Map<String, String> params = new HashMap<>();
		params.put("projectId", dto.getProject().getId().toString());
		params.put("username", dto.getUser().getFullName());
		params.put("projectName", dto.getProject().getName());
		params.put("noticeDate", Utiles.transform(dto.getPersonalExpenseSheetDto().getStartDate(), DATE_TIME_FORMAT));
		params.put("description", dto.getPersonalExpenseSheetDto().getDescription());
		
		loadTemplateAndSendMail(smtpMailFrom, dto.getEmail(), subject, "mail_template.jsp", params); // + dto.getLocale().getLanguage() + ".html", params);
	}
	
	@Async
	public void sendExpenseTeamLeaderMail(String to, User teamLeader, ExpenseSheet expenseSheet, Locale locale) {
		
		log.info("Preparando la plantilla de correo: expense_team_leader_mail_template_" + locale.getLanguage() + ".html");
		
		String subject = messageSource.getMessage("smtp.mail.expense.team.leader.subject", new Object[] { }, locale);
		
		Map<String, String> params = new HashMap<>();
		params.put("projectId", expenseSheet.getProject().getId().toString());
		params.put("username", teamLeader.getName() +  " " + teamLeader.getSurnames());
		params.put("projectName", expenseSheet.getProject().getName());
		params.put("noticeDate", Utiles.transformFormattedDateToString(expenseSheet.getCreationDate()));
		params.put("description", expenseSheet.getName());
		
		loadTemplateAndSendMail(smtpMailFrom, to, subject, "expense_team_leader_mail_template_" + locale.getLanguage() + ".html", params);
	}
	
	@Async
	public void sendExpenseRRHHMail(String to, User rrhh, ExpenseSheet expenseSheet, Locale locale) {
		
		log.info("Preparando la plantilla de correo: expense_rrhh_mail_template_" + locale.getLanguage() + ".html");
		
		String subject = messageSource.getMessage("smtp.mail.expense.rrhh.subject", new Object[] { }, locale);
		
		Map<String, String> params = new HashMap<>();
		params.put("projectId", expenseSheet.getProject().getId().toString());
		params.put("username", rrhh.getName() +  " " + rrhh.getSurnames());
		params.put("projectName", expenseSheet.getProject().getName());
		params.put("noticeDate", Utiles.transformFormattedDateToString(expenseSheet.getCreationDate()));
		params.put("description", expenseSheet.getName());
		
		loadTemplateAndSendMail(smtpMailFrom, to, subject, "expense_rrhh_mail_template_" + locale.getLanguage() + ".html", params);
	}

	@Async
	public void sendSigningInvalidMail(String to, User user, Locale locale) {
		
		log.info("Preparando la plantilla de correo: signing_invalid_mail_template_" + locale.getLanguage() + ".html");
		
		String subject = messageSource.getMessage("smtp.mail.signing.invalid.subject", new Object[] { }, locale);

		Map<String, String> params = new HashMap<>();
		params.put("todayDate", Utiles.getDateFormattedESP(new Date()));
		
		loadTemplateAndSendMail(smtpMailFrom, to, subject, "signing_invalid_mail_template_" + locale.getLanguage() + ".html", params);
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
	public void sendSigningModifyMail(String to, ModifiedSigning modifiedSigning, Locale locale) {

		log.info("Preparando la plantilla de correo: signing_modify_mail_template_" + locale.getLanguage() + ".html");

		String subject = messageSource.getMessage("smtp.mail.signing.manual.subject", new Object[] { }, locale);

		Map<String, String> params = new HashMap<>();
		params.put("username", modifiedSigning.getUser().getName() + " " + modifiedSigning.getUser().getSurnames());
		params.put("signingId", modifiedSigning.getSigningId().toString());
		params.put("userId", modifiedSigning.getUser().getId().toString());
		params.put("manualSigningType", modifiedSigning.getTypeId());
		params.put("startDate", Utiles.transform(modifiedSigning.getStartDate(), DATE_TIME_FORMAT));
		params.put("endDate", Utiles.transform(modifiedSigning.getEndDate(), DATE_TIME_FORMAT));

		loadTemplateAndSendMail(smtpMailFrom, to, subject, "signing_modify_mail_template_" + locale.getLanguage() + ".html", params);
	}

	@Async
	public void sendExpenseDeclineMail(String to, User user, ExpenseSheet expenseSheet, Locale locale) {
		
		log.info("Preparando la plantilla de correo: expense_decline_mail_template_" + locale.getLanguage() + ".html");
		
		String subject = messageSource.getMessage("smtp.mail.expense.decline.subject", new Object[] { }, locale);

		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(expenseSheet.getId()));
		params.put("description", expenseSheet.getName());
		params.put("username", user.getName() + " " + user.getSurnames());
		
		if (StringUtils.isNoneBlank(expenseSheet.getObservations())) {
			
			String observations = messageSource.getMessage("smtp.mail.expense.decline.observation", new Object[] { expenseSheet.getObservations() }, locale);

			params.put("observations", observations);
		}
		
		loadTemplateAndSendMail(smtpMailFrom, to, subject, "expense_decline_mail_template_" + locale.getLanguage() + ".html", params);
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

	@Async
	public void sendProjectWeeklySharesResume(String to, Project project, Locale locale) {

		log.info("Preparando la plantilla de correo: project_weekly_shares_resume_mail_template_" + locale.getLanguage() + ".html");

		String subject = messageSource.getMessage("smtp.mail.project.weekly.shares.resume.subject", new Object[] { project.getName() }, locale);

		Map<String, String> params = new HashMap<>();
		params.put("projectId", project.getId().toString());
		params.put("projectName", project.getName());

		loadTemplateAndSendMail(smtpMailFrom, to, subject, "project_weekly_shares_resume_mail_template_" + locale.getLanguage() + ".html", params);
	}
	
	@SuppressWarnings("unused")
	private void sendMail(String from, String to, String subject, String content) {
		
		try {
			
			if (StringUtils.isEmpty(to)) {
				log.error("El email del destinatario es vacío o está mal formulado.");
				return;
			}
			
			SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setFrom(from);
	        message.setTo(to); 
	        message.setSubject(subject); 
	        message.setText(content);
	        emailSender.send(message);
        
		} catch (Exception e) {
			log.error("Error al enviar un correo electronico para " + to, e);
		}
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
	
	private void sendPDFShareHtmlMail(Object share, String type, byte[] pdfGenerated, String from, String to, String subject, String content, Locale locale) {

		try {
			
			if (StringUtils.isEmpty(to)) {
				log.error("El email del destinatario es vacío o está mal formulado.");
				return;
			}

	        // now write the PDF content to the output stream

	        String fileName = null;
	        
	        if ("cs".equals(type)) {
	        	
	        	ConstructionShare transformedShare = (ConstructionShare) share;
	        	
	        	if (transformedShare == null) {
	        		log.error("No se ha encontrado el parte de construccion.");
	        		return;
	        	}
				
	        	log.info("Adjuntando parte de construccion " + transformedShare.getId() + " en " + locale.getLanguage());

				fileName = messageSource.getMessage("shares.construction.pdf.name", new Object[] { transformedShare.getId().toString(), Utiles.getDateFormatted(transformedShare.getStartDate()) }, locale) + ".pdf";

			} else if ("is".equals(type)) {
	        	
	        	InspectionDto inspection = (InspectionDto) share;
	        	
	        	if (inspection == null) {
	        		log.error("No se ha encontrado la revisión.");
	        		return;
	        	}

	        	log.info("Adjuntando revisión " + inspection.getId() + " en " + locale.getLanguage());

				fileName = messageSource.getMessage("shares.no.programmed.pdf.name", new Object[] {
						inspection.getId(),
						Utiles.getDateFormatted(inspection.getStartDate())
				}, locale) + ".pdf";
				
	        } else if ("ips".equals(type)) {
	        	
	        	InterventionPrShare transformedShare = (InterventionPrShare) share;
	        	
	        	if (transformedShare == null) {
	        		log.error("No se ha encontrado el parte programado.");
	        		return;
	        	}
	        	
	        	log.info("Adjuntando parte programado " + transformedShare.getId() + " en " + locale.getLanguage());
	        	
	        	fileName = messageSource.getMessage("shares.programmed.pdf.name", new Object[] { transformedShare.getId().toString(), Utiles.getDateFormatted(transformedShare.getStartDate()) }, locale) + ".pdf";
	        } else if ("ws".equals(type)) {
	        	
	        	WorkShare transformedShare = (WorkShare) share;
	        	
	        	if (transformedShare == null) {
	        		log.error("No se ha encontrado el parte de trabajo.");
	        		return;
	        	}
	        	
	        	log.info("Adjuntando parte de trabajo " + transformedShare.getId() + " en " + locale.getLanguage());
	        	
	        	fileName = messageSource.getMessage("shares.work.pdf.name", new Object[] { transformedShare.getId().toString(), Utiles.getDateFormatted(transformedShare.getStartDate()) }, locale) + ".pdf";
	        }

	        // log fileName
	        log.info("Adjuntando fichero (" + locale.getLanguage() + ") " + fileName);
	        
	        // construct the text body part
	        MimeBodyPart textBodyPart = new MimeBodyPart();
	        textBodyPart.setText(content, "UTF-8", "html");
	        
			// construct the pdf body part
	        DataSource dataSource = new ByteArrayDataSource(pdfGenerated, "application/pdf");
	        MimeBodyPart pdfBodyPart = new MimeBodyPart();
	        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
	        pdfBodyPart.setFileName(fileName);
	        
	        // construct the mime multi part
	        MimeMultipart mimeMultipart = new MimeMultipart();
	        mimeMultipart.addBodyPart(textBodyPart);
	        mimeMultipart.addBodyPart(pdfBodyPart);
	        
	        // create the sender/recipient addresses
	        InternetAddress iaSender = new InternetAddress(from);
	        InternetAddress iaRecipient = new InternetAddress(to);
	        
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			mimeMessage.setFrom(iaSender);
			mimeMessage.setSender(iaSender);
	        mimeMessage.setSubject(subject);
	        mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
	        mimeMessage.setContent(mimeMultipart);
		
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
	
	private void loadPDFTemplateAndSendMail(Object share, String type, byte[] pdfGenerated, String from, String to, String subject, String templateName, Map<String, String> params, Locale locale) {
		
		try {
			
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/mail/" + templateName);
			String templateContent = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
			
			for (Map.Entry<String, String> entry : params.entrySet()) {
				
				if (entry.getValue() == null) {
					continue;
				}
					
				templateContent = templateContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
			}
			
			sendPDFShareHtmlMail(share, type, pdfGenerated, from, to, subject, templateContent, locale);
			
		} catch (Exception e) {
			log.error("Error al cargar el template " + templateName + " y enviar el correo para " + to, e);
		}
	}
}
