package com.mendusa.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    // todo: the sender is usually same person, either its hardcoded or removed
    private String sender;
    private String recipient;

    private String messageBody;

    private String subject;

    // todo: still no attachment name
    // todo: attachments are usually more than one, how would you handle that with this
    private byte[] attachment;
    // todo: what are you doing with filepath here
    private String filePath;

}
