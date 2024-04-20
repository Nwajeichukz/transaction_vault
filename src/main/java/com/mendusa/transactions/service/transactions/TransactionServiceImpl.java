package com.mendusa.transactions.service.transactions;

import com.mendusa.transactions.dto.AppResponse;
import com.mendusa.transactions.dto.EmailDto;
import com.mendusa.transactions.dto.FileAttachment;
import com.mendusa.transactions.dto.RecentTransactionResponse;
import com.mendusa.transactions.repository.TransactionRepository;
import com.mendusa.transactions.service.email.EmailService;
import com.mendusa.transactions.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

        byte[] fileContent = Utils.writeToCsv(getListOfTransactions());
        final FileAttachment attachment = new FileAttachment(
                RandomStringUtils.randomAlphanumeric(25).concat(".csv"),
                fileContent);

        this.sendTransactionFile(attachment);
        return new AppResponse<>(0, "successful");

    }


    private List<RecentTransactionResponse> getListOfTransactions() {
        return repository.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")))
                .map(RecentTransactionResponse::new)
                .getContent();
    }

    private void sendTransactionFile( final FileAttachment attachment) {
        EmailDto emailDto = EmailDto.builder()
                .recipient("nwajeigoddowell@gmail.com")
                .subject("RECEIPT")
                .messageBody("Here is your receipts for your last 10 transactions") // todo: use an html template later
                .attachment(Collections.singletonList(attachment))
                .build();

                emailService.sendAsync(emailDto);


    }

}