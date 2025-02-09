package com.epm.gestepm.emailapi.service;

import com.epm.gestepm.emailapi.dto.EmailGroup;

public interface EmailService {
    void sendEmail(final EmailGroup emailGroup);
}
