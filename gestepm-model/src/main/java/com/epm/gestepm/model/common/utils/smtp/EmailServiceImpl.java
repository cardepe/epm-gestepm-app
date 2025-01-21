package com.epm.gestepm.model.common.utils.smtp;

import com.epm.gestepm.modelapi.common.utils.smtp.EmailService;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.EmailTemplateDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${gestepm.base.url}")
    private String appUrlBase;

    private final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void process(final EmailTemplateDto template) {

        try {

            final RestTemplate restTemplate = new RestTemplate();
            final String templateContent = restTemplate.getForObject(appUrlBase + "/templates/email", String.class);

            final String result = template.getParams().entrySet().stream()
                    .filter(entry -> StringUtils.isNoneBlank(entry.getValue()))
                    .reduce(templateContent,
                            (content, entry) -> content.replace("{{" + entry.getKey() + "}}", entry.getValue()),
                            (c1, c2) -> c1
                    );

            send(template.getFrom(), template.getTo(), template.getSubject(), result);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void send(String from, String to, String subject, String content) {

        try {

            final MimeMessage mimeMessage = emailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            emailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
