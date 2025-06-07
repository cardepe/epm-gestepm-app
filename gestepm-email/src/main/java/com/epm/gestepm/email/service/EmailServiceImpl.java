package com.epm.gestepm.email.service;

import com.epm.gestepm.emailapi.dto.Attachment;
import com.epm.gestepm.emailapi.dto.EmailGroup;
import com.epm.gestepm.emailapi.service.EmailService;
import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import java.util.Base64;
import java.util.Map;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.*;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;

@Data
@Service
@EnableExecutionLog(layerMarker = PROCESS_SERVICE)
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;

    private final LocaleResolver localeResolver;

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Async
    @Override
    @LogExecution(operation = OP_PROCESS,
            debugOut = true,
            msgIn = "Sending email",
            msgOut = "Sending email OK",
            errorMsg = "Failed to send email")
    public void sendEmail(final EmailGroup emailData) {
        try {
            final MimeMessage message = this.mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            final Map<String, Object> variables = emailData.buildVariables();
            variables.put("locale", emailData.getLocale());

            final Context context = new Context(emailData.getLocale());
            context.setVariables(variables);
            final String htmlContent = this.templateEngine.process("main.html", context);

            helper.setTo(emailData.getEmails().toArray(new String[0]));
            helper.setSubject(emailData.getSubject());
            helper.setText(htmlContent, true);
            helper.setFrom(this.from);

            if (emailData.containsAttachments()) {
                for (final Attachment attachment : emailData.getAttachments()) {
                    helper.addAttachment(attachment.getFileName(), new ByteArrayDataSource(Base64.getDecoder().decode(attachment.getFileData()), attachment.getContentType()));
                }
            }

            this.mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
