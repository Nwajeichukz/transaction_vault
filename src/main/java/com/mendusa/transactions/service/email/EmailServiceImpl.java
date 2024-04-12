package com.mendusa.transactions.service.email;



import com.mendusa.transactions.dto.EmailDto;
import com.mendusa.transactions.dto.FileAttachment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Slf4j
@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendAsync(EmailDto emailDto){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, CollectionUtils.isNotEmpty(emailDto.getAttachment()));

            helper.setFrom("nwajeigoddowell@gmail.com");// todo: put in properties file
            helper.setTo(emailDto.getRecipient());
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getMessageBody());


            for (FileAttachment attachments : emailDto.getAttachment()){
                ByteArrayResource fileAttachment = new ByteArrayResource(attachments.getAttachment());
                helper.addAttachment(attachments.getFileName(), fileAttachment);
            }

            javaMailSender.send(message);

        } catch (Exception e) {
            log.error("An error occurred while trying to send mail {}", e.getMessage());
        }
    }
}