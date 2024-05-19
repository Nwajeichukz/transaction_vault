package com.mendusa.transactions.service.transactions;

import com.mendusa.transactions.dto.*;
import com.mendusa.transactions.repository.TransactionRepository;
import com.mendusa.transactions.service.email.EmailService;
import com.mendusa.transactions.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;


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

        return new AppResponse<>(0, "Successful", getMethodSuccessRate() );
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

    private void sendTransactionFile( final FileAttachment attachment) {
        EmailDto emailDto = EmailDto.builder()
                .recipient("nwajeigoddowell@gmail.com")
                .subject("RECEIPT")
                .messageBody("Here is your receipts for your last 10 transactions") // todo: use an html template later
                .attachment(Collections.singletonList(attachment))
                .build();

                emailService.sendAsync(emailDto);


    }

    private List<PaymentRateResponse> getMethodSuccessRate(){
        List<String> paymentsMethods = List.of("CARD","TRANSFER", "PAYWITHTRANSFER");

        List<List<PaymentRateCount>> totalTransactionList = new ArrayList<>();

        for (String paymentMethod: paymentsMethods) {
           totalTransactionList.add(Utils.getMethodSuccessRate(transactionMethodList(), paymentMethod));
        }

        return Utils.calculateSuccessAndFailedPercentage(totalTransactionList);

    }

    private List<PaymentRateResponse> getProviderSuccessRate(){
        List<String> paymentProviders = List.of("INTERSWITCHSL", "INTERSWITCH", "MEDUSA_FCMB", "MEDUSA_FCMB_ONUS",
                "NIBSSMEDUSA", "MAGTIPON","FCMB", "NIBSS", "BUILD MFB", "PARALLEX", "OPTIMUS BANK",
                "NAVYMFB", "Build", "INTERSWITCH_SUPER", "MEDUSA_ACCESS", "Providus Bank", "TAJBank", "LOTUS BANK");


        List<List<PaymentRateCount>> totalTransactionList = new ArrayList<>();

        for (String paymentProvider: paymentProviders) {
            totalTransactionList.add(Utils.getProviderSuccess(transactionProviderList(), paymentProvider));
        }

        return Utils.calculateSuccessAndFailedPercentage(totalTransactionList);
    }

    private List<Tuple> transactionMethodList() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -24);
        Date last24Hours = cal.getTime();
        return transactionRepository.findTransactionCountByResponseCodeAndPaymentMethod(last24Hours);


//        return transactionRepository.findAll().stream()
//                .map(TransactionDto::new).collect(Collectors.toList());

//        return transactionRepository.findAll();

//return null;

    }


    private List<Tuple> transactionProviderList() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -24);
        Date last24Hours = cal.getTime();

        return transactionRepository.findTransactionCountByResponseCodeAndProvider(last24Hours);
    }

}