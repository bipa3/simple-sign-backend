package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyResDTO {

    private int replyId;
    private String replyContent;
    private int approvalDocId;
    private int upperReplyId;
    private LocalDateTime regDate;
    private int depth;
    private int groupNo;
    private int groupOrd;
    private int orgUserId;

}
