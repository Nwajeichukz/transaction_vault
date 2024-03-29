package com.mendusa.transactions.service.transactions;

import com.mendusa.transactions.dto.AppResponse;
import com.mendusa.transactions.dto.EmailDto;
import com.mendusa.transactions.dto.RecentTransactionResponse;
import com.mendusa.transactions.repository.TransactionRepository;
import com.mendusa.transactions.service.email.EmailServiceImpl;
import com.mendusa.transactions.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    private final EmailServiceImpl emailService;


    @Override
    public AppResponse<List<RecentTransactionResponse>> getTransactionList() {

        return new AppResponse<>(0, "Successful", getListOfTransactions());
    }

    @Override
    public AppResponse<String> getAndSendToMail() {
        // todo: this is not readable
        byte[] resultByteArray = Utils.writeToCsv(getListOfTransactions());


        EmailDto emailDto = EmailDto.builder()
                .recipient("nwajeigoddowell@gmail.com")
                .subject("RECEIPT")
                .messageBody("Here is your receipts for your last 10 transactions")
                .attachment(resultByteArray) //todo: attachment cannot always be singular
                .build();

        try{
            emailService.sendToEmail(emailDto);
        }catch(MessagingException e){
            e.printStackTrace();
        }

        return new AppResponse<>(0, "successful");

    }


    private List<RecentTransactionResponse> getListOfTransactions() {
        Page<RecentTransactionResponse> page = repository.findAll( //todo: this page variable is unneccesary
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"))).map(RecentTransactionResponse::new);


        return page.getContent();
    }

}