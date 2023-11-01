package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.ReplyDAO;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import bitedu.bipa.simplesignbackend.validation.CustomErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyService {

    private final ReplyDAO replyDAO;

    public ReplyService(ReplyDAO replyDAO) {
        this.replyDAO = replyDAO;
    }

    public List<ReplyResDTO> showReplyList(int approvalDocId) {
        List<ReplyResDTO> list = replyDAO.selectAllReplyList(approvalDocId);
        List<ReplyResDTO> filteredList = list.stream()
                .filter(reply -> !"02".equals(reply.getFileCode()))
                .collect(Collectors.toList());
        return filteredList;
    }

    @Transactional
    public int registerReply(ReplyReqDTO replyReqDTO) {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        replyReqDTO.setOrgUserId(orgUserId);
        //upperReplyId 가 0이면 댓글 아니면 대댓글
        int replyId = 0;
        if(replyReqDTO.getUpperReplyId() ==0) {
             replyId = replyDAO.insertReply(replyReqDTO);
        }else {
            replyDAO.insertLowerReply(replyReqDTO);
        }
        return replyId;
    }

    @Transactional
    public void updateReply(ReplyReqDTO replyReqDTO) {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        //댓글이 본인이 작성한 것인지 확인
        int replierId = replyDAO.selectReplierId(replyReqDTO.getReplyId());
        if(replierId !=orgUserId) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }
        int affectedCount = replyDAO.updateReply(replyReqDTO);
        if(affectedCount ==0) {
            throw new RestApiException(CustomErrorCode.REPLY_UPDATE_FAIL);
        }
    }

    @Transactional
    public void removeReply(int replyId) {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        //댓글이 본인이 작성한 것인지 확인
        ReplyResDTO replyResDTO = replyDAO.selectReplierAndDepth(replyId);
        if(replyResDTO.getOrgUserId() !=orgUserId) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }
        int affectedCount = replyDAO.deleteReply(replyId);
        //댓글 depth 가 1이라면 아래 댓글도 모두 삭제
        if(replyResDTO.getDepth() ==1) {
            replyDAO.deleteLowerReply(replyId);
        }
        if(affectedCount ==0) {
            throw new RestApiException(CustomErrorCode.REPLY_DELETE_FAIL);
        }
    }

    public boolean showIsEditable(int replyId) {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        int replier = replyDAO.selectReplierId(replyId);
        if(orgUserId ==replier) {
            return true;
        }
        return false;
    }

    @Transactional
    public void insertReplyAttachment(String s3Url, String fileName, int replyId, String uniqueFileName) {
        ReplyAttachmentDTO replyAttachmentDTO = new ReplyAttachmentDTO();
        replyAttachmentDTO.setReplyId(replyId);
        replyAttachmentDTO.setApprovalFilePath(s3Url);
        replyAttachmentDTO.setFileName(fileName);
        replyAttachmentDTO.setDownloadFilePath(uniqueFileName);
        int affectedCount = replyDAO.insertReplyAttachment(replyAttachmentDTO);
        if(affectedCount==0) {
            throw new RestApiException(CustomErrorCode.APPROVAL_FAIL);
        }
    }

    public List<FileResDTO> getFileNames(int replyId) {
        FileResDTO fileResDTO = new FileResDTO();
        return replyDAO.selectFileNamesAndFilePath(replyId);
    }
}
