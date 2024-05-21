package com.mendusa.transactions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Tuple;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRateCount {
    private String payMethod;
    private int total;
    private int success;

    @JsonIgnore
    private String rc;

    public PaymentRateCount(String payMethod, int total, int success) {
        this.payMethod = payMethod;
        this.total = total;
        this.success = success;
    }


    public static PaymentRateCount from(Tuple tuple){
        String rc = tuple.get("responseCode", String.class);
        String provider = tuple.get("provider", String.class);
        Long count = tuple.get("totalCount", Long.class);
        return
                new PaymentRateCount(provider, count.intValue(), 0, rc);
    }
}
