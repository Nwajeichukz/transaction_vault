package com.mendusa.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    private String recipient;

    private String messageBody;

    private String subject;


    private List<ByteAttachmentAndFileNameDto> attachment;

}
