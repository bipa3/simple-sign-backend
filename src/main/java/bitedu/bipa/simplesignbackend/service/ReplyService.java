package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.ReplyDAO;
import bitedu.bipa.simplesignbackend.model.dto.ReplyReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.ReplyResDTO;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
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
    public void registerReply(ReplyReqDTO replyReqDTO) {
        int orgUserId = (int) SessionUtils.getAttribute("userId");
        replyReqDTO.setOrgUserId(orgUserId);
        //upperReplyId 가 0이면 댓글 아니면 대댓글
        if(replyReqDTO.getUpperReplyId() ==0) {
            replyDAO.insertReply(replyReqDTO);
        }else {
            replyDAO.insertLowerReply(replyReqDTO);
        }

    }

    @Transactional
    public void updateReply(ReplyReqDTO replyReqDTO) {
        int orgUserId = (int) SessionUtils.getAttribute("userId");
        //댓글이 본인이 작성한 것인지 확인
        int replierId = replyDAO.selectReplierId(replyReqDTO.getReplyId());
        if(replierId !=orgUserId) {
            throw new RuntimeException(); //권한이 없음
        }
        int affectedCount = replyDAO.updateReply(replyReqDTO);
        if(affectedCount ==0) {
            throw new RuntimeException(); //댓글 수정 안됨
        }
    }

    @Transactional
    public void removeReply(int replyId) {
        int orgUserId = (int) SessionUtils.getAttribute("userId");
        //댓글이 본인이 작성한 것인지 확인
        int replierId = replyDAO.selectReplierId(replyId);
        if(replierId !=orgUserId) {
            throw new RuntimeException(); //권한이 없습니다.
        }
        int affectedCount = replyDAO.deleteReply(replyId);
        if(affectedCount ==0) {
            throw new RuntimeException(); //댓글 삭제 실패
        }
    }

    public boolean showIsEditable(int replyId) {
        int orgUserId = (int) SessionUtils.getAttribute("userId");
        int replier = replyDAO.selectReplierId(replyId);
        if(orgUserId ==replier) {
            return true;
        }
        return false;
    }
}
