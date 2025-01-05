package com.epm.gestepm.modelapi.common.utils.smtp;

import com.epm.gestepm.modelapi.common.utils.smtp.dto.EmailTemplateDto;

public interface EmailService {
    void process(final EmailTemplateDto template);
}
