package com.mendusa.transactions.service.email;


import com.mendusa.transactions.dto.EmailDto;

import javax.mail.MessagingException;


// todo: why's the implementation the interface??
public interface EmailServiceImpl {
    void sendToEmail(EmailDto  emailDto) throws MessagingException;
}
