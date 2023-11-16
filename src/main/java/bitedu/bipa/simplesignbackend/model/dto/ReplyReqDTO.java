package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;
import org.springframework.cglib.core.Local;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class ReplyReqDTO {

    private int replyId;
    private int approvalDocId;
    private int upperReplyId;
    @NotEmpty(message = "내용을 입력하세요.")
    private String replyContent;
    private int groupNo;
    private LocalDateTime regDate;
    private int orgUserId;
    private LocalDateTime updateDate;
}
