package com.mendusa.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Tuple;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDto {

    private String provider;

    private int count;

    private String responseCode;

    public static ProviderDto from(Tuple tuple){
        String responseCode  = tuple.get("responseCode", String.class);
        String provider = tuple.get("provider", String.class);
        Long count = tuple.get("totalCount", Long.class);
        return
                new ProviderDto(provider, count.intValue(), responseCode);
    }
}
