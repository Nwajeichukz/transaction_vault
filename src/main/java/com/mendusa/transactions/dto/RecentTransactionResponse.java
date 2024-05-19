package com.mendusa.transactions.dto;


import com.mendusa.transactions.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentTransactionResponse {
    private String responseCode;

    private String transactionDate;

    private String transactionId;

    private String amount;

    private String paymentMethod;

    public RecentTransactionResponse(Transaction transaction) {
        this.responseCode = transaction.getResponseCode();
        this.transactionDate = transaction.getTransactionDate().toString();
        this.transactionId = transaction.getTransactionId();
        this.amount = transaction.getAmount().toString();
        this.paymentMethod = transaction.getPaymentMethod();
    }



}
