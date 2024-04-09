package com.mendusa.transactions.service.transactions;

import com.mendusa.transactions.dto.AppResponse;
import com.mendusa.transactions.dto.EmailDto;
import com.mendusa.transactions.dto.FileNameAndAttachment;
import com.mendusa.transactions.dto.RecentTransactionResponse;
import com.mendusa.transactions.repository.TransactionRepository;
import com.mendusa.transactions.service.email.EmailService;
import com.mendusa.transactions.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    private final EmailService emailService;


    @Override
    public AppResponse<List<RecentTransactionResponse>> getTransactionList() {

        return new AppResponse<>(0, "Successful", getListOfTransactions());
    }

    @Override
    public AppResponse<String> getAndSendToMail() {

        List<FileNameAndAttachment> resultByteArrayInAlist = List.of(Utils.writeToCsv(getListOfTransactions()));

        EmailDto emailDto = EmailDto.builder()
                .recipient("nwajeigoddowell@gmail.com")
                .subject("RECEIPT")
                .messageBody("Here is your receipts for your last 10 transactions")
                .attachment(resultByteArrayInAlist)
                .build();

        try{
            emailService.sendToEmail(emailDto);
        }catch(MessagingException e){
            e.printStackTrace();
        }

        return new AppResponse<>(0, "successful");

    }


    private List<RecentTransactionResponse> getListOfTransactions() {
        return repository.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")))
                .map(RecentTransactionResponse::new)
                .getContent();
    }

}