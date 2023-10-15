package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.ReplyDAO;
import bitedu.bipa.simplesignbackend.model.dto.ReplyReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.ReplyResDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReplyService {

    private final ReplyDAO replyDAO;

    public ReplyService(ReplyDAO replyDAO) {
        this.replyDAO = replyDAO;
    }

    public List<ReplyResDTO> showReplyList(int approvalDocId) {
        return replyDAO.selectAllReplyList(approvalDocId);
    }

    @Transactional
    public void registerReply(ReplyReqDTO replyReqDTO, int userId) {
        replyReqDTO.setOrgUserId(userId);
        //upperReplyId 가 0이면 댓글 아니면 대댓글
        if(replyReqDTO.getUpperReplyId() ==0) {
            replyDAO.insertReply(replyReqDTO);
        }else {
            replyDAO.insertLowerReply(replyReqDTO);
        }

    }

    public void updateReply(ReplyReqDTO replyReqDTO) {
        int affectedCount = replyDAO.updateReply(replyReqDTO);
        if(affectedCount ==0) {
            throw new RuntimeException(); //댓글 수정 안됨
        }
    }

    public void removeReply(int replyId) {
        int affectedCount = replyDAO.deleteReply(replyId);
        if(affectedCount ==0) {
            throw new RuntimeException(); //댓글 삭제 실패
        }
    }
}
