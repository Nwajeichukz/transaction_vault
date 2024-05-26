package com.mendusa.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Tuple;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodDto {

    private String method;

    private int count;

    private String responseCode;

    public static MethodDto from(Tuple tuple){
        String responseCode = tuple.get("responseCode", String.class);
        String method = tuple.get("paymentMethod", String.class);
        Long count = tuple.get("totalCount", Long.class);
        return
                new MethodDto(method, count.intValue(), responseCode);
    }
}
