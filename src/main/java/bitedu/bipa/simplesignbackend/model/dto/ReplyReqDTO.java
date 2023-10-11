package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyReqDTO {

    private int replyId;
    private int approvalDocId;
    private int upperReplyId;
    private String replyContent;
    private int groupNo;
    private LocalDateTime regDate;
    private int orgUserId;
}
