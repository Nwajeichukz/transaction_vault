package com.mendusa.transactions.dto;


import com.mendusa.transactions.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentTransactionResponse {
    // todo: why are you like this sef, i told about this naming convention and you didn't change it
    private String response_code;

    private String transaction_date;

    private String transaction_id;

    private String amount;

    private String payment_method;

    public RecentTransactionResponse(Transaction transaction) {
        this.response_code = transaction.getResponse_code();
        this.transaction_date = transaction.getTransaction_date().toString();
        this.transaction_id = transaction.getTransaction_id();
        this.amount = transaction.getAmount().toString();
        this.payment_method = transaction.getPayment_method();
    }



}
