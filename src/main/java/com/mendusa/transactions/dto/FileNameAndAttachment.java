package com.mendusa.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileNameAndAttachment {
    private String fileName;
    private byte[] attachment;


}
