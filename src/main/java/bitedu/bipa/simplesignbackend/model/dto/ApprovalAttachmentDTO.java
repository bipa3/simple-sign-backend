package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class ApprovalAttachmentDTO {

    private String fileName;
    private String approvalFilePath;
    private int approvalDocId;

}
