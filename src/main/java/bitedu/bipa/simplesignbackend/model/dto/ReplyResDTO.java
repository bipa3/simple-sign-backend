package bitedu.bipa.simplesignbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyResDTO {

    private int replyId;
    private String replyContent;
    private int approvalDocId;
    private int upperReplyId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime regDate;
    private int depth;
    private int groupNo;
    private int groupOrd;
    private int orgUserId;
    private String userName;
    private String approvalFilePath;
    private String fileCode;

}
