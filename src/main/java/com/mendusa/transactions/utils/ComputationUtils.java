package com.mendusa.transactions.utils;

import com.mendusa.transactions.dto.PaymentRateCount;
import com.mendusa.transactions.dto.PaymentRateResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComputationUtils {

    public static List<PaymentRateResponse> calculateSuccessAndFailedPercentage(Collection<PaymentRateCount> rateCountList) {
        List<PaymentRateResponse> transactionList = new ArrayList<>();

        for (PaymentRateCount paymentRateCount : rateCountList) {
            int total = paymentRateCount.getTotal();
            int success = paymentRateCount.getSuccess();
            String successPercentage = calculatePercentage(total, success) + "%";
            String failedPercentage = calculatePercentage(total,
                    (total - success)) + "%";
            PaymentRateResponse paymentRateResponse = PaymentRateResponse.builder()
                    .name(paymentRateCount.getPayMethod())
                    .success(successPercentage)
                    .failed(failedPercentage)
                    .build();

            transactionList.add(paymentRateResponse);
        }
        return transactionList;
    }

    private static int calculatePercentage(int totalTransaction, int successFullTransaction){
        if(totalTransaction == 0) return 0;

        return (100 * successFullTransaction)/totalTransaction;
    }

}
