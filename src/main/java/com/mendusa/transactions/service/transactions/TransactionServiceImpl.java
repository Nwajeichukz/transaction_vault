package com.mendusa.transactions.service.transactions;

import com.mendusa.transactions.dto.*;
import com.mendusa.transactions.repository.TransactionRepository;
import com.mendusa.transactions.service.email.EmailService;
import com.mendusa.transactions.utils.ComputationUtils;
import com.mendusa.transactions.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

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

    @Override
    public AppResponse<List<PaymentRateResponse>> methodSuccessRate() {

        return new AppResponse<>(0, "Successful", getMethodSuccessRate());
    }

    @Override
    public AppResponse<List<PaymentRateResponse>> providerSuccessRate() {
        return new AppResponse<>(0, "Successful", getProviderSuccessRate());
    }


    private List<RecentTransactionResponse> getListOfTransactions() {
        return transactionRepository.findAll(
                        PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")))
                .map(RecentTransactionResponse::new)
                .getContent();
    }

    private void sendTransactionFile(final FileAttachment attachment) {
        EmailDto emailDto = EmailDto.builder()
                .recipient("nwajeigoddowell@gmail.com")
                .subject("RECEIPT")
                .messageBody("Here is your receipts for your last 10 transactions") // todo: use an html template later
                .attachment(Collections.singletonList(attachment))
                .build();

        emailService.sendAsync(emailDto);


    }

    private List<PaymentRateResponse> getMethodSuccessRate() {
        List<MethodDto> methodTransactions = transactionMethodList();


        Map<String, PaymentRateCount> rateCountMap = Utils.getMethodSuccessRate(methodTransactions);


        return ComputationUtils.calculateSuccessAndFailedPercentage(rateCountMap.values());

    }

    private List<PaymentRateResponse> getProviderSuccessRate() {

        List<ProviderDto> providerTransactions = transactionProviderList();

        Map<String, PaymentRateCount> rateCountMap = Utils.getProviderSuccessRate(providerTransactions);

        return ComputationUtils.calculateSuccessAndFailedPercentage(rateCountMap.values());
    }

    private List<MethodDto> transactionMethodList() {

        return transactionRepository
                .findTransactionCountByResponseCodeAndPaymentMethod(DateUtils.addDays(new Date(), -1))
                .stream().map(MethodDto::from)
                .collect(Collectors.toList());
    }


    private List<ProviderDto> transactionProviderList() {

        return transactionRepository
                .findTransactionCountByResponseCodeAndProvider(DateUtils.addDays(new Date(), -1))
                .stream().map(ProviderDto::from)
                .collect(Collectors.toList());
    }

}