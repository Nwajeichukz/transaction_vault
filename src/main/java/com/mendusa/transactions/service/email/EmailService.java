package com.mendusa.transactions.service.email;



import com.mendusa.transactions.Exception.ApiException;
import com.mendusa.transactions.dto.EmailDto;
import com.mendusa.transactions.utils.TrUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailService implements EmailServiceImpl {

    private final JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendToEmail(EmailDto emailDto){


        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true); //todo: set to true only when there's attachment

            helper.setFrom("nwajeigoddowell@gmail.com");
            helper.setTo(emailDto.getRecipient());
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getMessageBody());


            ByteArrayResource fileAttachment = new ByteArrayResource(emailDto.getAttachment());
            helper.addAttachment("data.xlsx", fileAttachment); //todo : attachment name should not be hard-coded

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}