package com.mendusa.transactions.service.email;


import com.mendusa.transactions.dto.EmailDto;

import javax.mail.MessagingException;


public interface EmailService {
    void sendAsync(EmailDto  emailDto) ;
}
