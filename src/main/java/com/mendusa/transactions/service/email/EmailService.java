package com.mendusa.transactions.service.email;



import com.mendusa.transactions.dto.EmailDto;
import com.mendusa.transactions.dto.FileNameAndAttachment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailService implements EmailServiceImpl {

    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendToEmail(EmailDto emailDto){
            boolean choice = true;

        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true); //todo: set to true only when there's attachment

            helper.setFrom("nwajeigoddowell@gmail.com");
            helper.setTo(emailDto.getRecipient());
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getMessageBody());


            for (FileNameAndAttachment attachments : emailDto.getAttachment()){
                ByteArrayResource fileAttachment = new ByteArrayResource(attachments.getAttachment());
                helper.addAttachment(attachments.getFileName(), fileAttachment);
            }

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}