package com.mendusa.transactions.service.transactions;

import com.mendusa.transactions.dto.AppResponse;
import com.mendusa.transactions.dto.PaymentRateCount;
import com.mendusa.transactions.dto.RecentTransactionResponse;
import com.mendusa.transactions.dto.PaymentRateResponse;

import javax.mail.MessagingException;
import java.util.List;

public interface TransactionService {
    AppResponse<List<RecentTransactionResponse>> getTransactionList();

    AppResponse<String> getAndSendToMail() throws IllegalAccessException, MessagingException;

    AppResponse<List<PaymentRateResponse>> methodSuccessRate();
    AppResponse<List<PaymentRateResponse>> providerSuccessRate();
}
