package com.mendusa.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRateCount {
    private String payMethod;
    private int total;
    private int success;
}
