package com.mendusa.transactions.controller;

import com.mendusa.transactions.dto.AppResponse;
import com.mendusa.transactions.dto.RecentTransactionResponse;
import com.mendusa.transactions.dto.PaymentRateResponse;
import com.mendusa.transactions.service.transactions.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController {


    private final TransactionService transaction;

    @GetMapping("/recentTransaction")
    public ResponseEntity<AppResponse<List<RecentTransactionResponse>>> getRecentTransaction() {
        return ResponseEntity.ok(transaction.getTransactionList());
    }


    @GetMapping("/mail")
    public ResponseEntity<AppResponse<String>> transactionReceiptToMAil() throws IllegalAccessException, MessagingException {
        return ResponseEntity.ok(transaction.getAndSendToMail());
    }

    @GetMapping("/methodSuccess")
    public ResponseEntity<AppResponse<List<PaymentRateResponse>>> getMethodSuccessRate(){
        return ResponseEntity.ok(transaction.methodSuccessRate());
    }


    @GetMapping("/providerSuccess")
    public ResponseEntity<AppResponse<List<PaymentRateResponse>>> getProviderSuccessRate(){
        return ResponseEntity.ok(transaction.providerSuccessRate());
    }


}