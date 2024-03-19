package com.mendusa.transactions.service.email;


import com.mendusa.transactions.dto.EmailDto;

import javax.mail.MessagingException;

public interface EmailServiceImpl {
    void sendToEmail(EmailDto  emailDto) throws MessagingException;
}
