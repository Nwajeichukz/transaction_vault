package com.mendusa.transactions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Tuple;

@Data
public class PaymentRateCount {
    private String payMethod;
    private int total;
    private int success;


    public PaymentRateCount(String payMethod, int total, int success) {
        this.payMethod = payMethod;
        this.total = total;
        this.success = success;
    }
}
