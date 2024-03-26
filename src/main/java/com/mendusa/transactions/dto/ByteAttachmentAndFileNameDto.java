package com.mendusa.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ByteAttachmentAndFileNameDto {
    private String fileName;

    private byte[] attachment;


}
