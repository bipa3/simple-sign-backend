package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class ReplyAttachmentDTO {

    private String fileName;
    private String approvalFilePath;
    private int replyId;
    private String downloadFilePath;
}
