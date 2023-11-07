package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.ApproveDAO;
import bitedu.bipa.simplesignbackend.dao.ReplyDAO;
import bitedu.bipa.simplesignbackend.enums.AlarmStatus;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import bitedu.bipa.simplesignbackend.validation.CustomErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyService {

    private final ReplyDAO replyDAO;
    private final AlarmService alarmService;
    private final ApproveDAO approveDAO;

    public ReplyService(ReplyDAO replyDAO, AlarmService alarmService, ApproveDAO approveDAO) {
        this.replyDAO = replyDAO;
        this.alarmService = alarmService;
        this.approveDAO = approveDAO;
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
        replyReqDTO.setRegDate(LocalDateTime.now());
        System.out.println(LocalDateTime.now());
        //upperReplyId 가 0이면 댓글 아니면 대댓글
        int replyId = 0;
        if(replyReqDTO.getUpperReplyId() ==0) {
             replyId = replyDAO.insertReply(replyReqDTO);
        }else {
            replyDAO.insertLowerReply(replyReqDTO);
        }
        //알림생성
        this.createAlarm(replyReqDTO, "insert");
        return replyId;
    }

    @Transactional
    public void updateReply(ReplyReqDTO replyReqDTO) {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        //댓글이 본인이 작성한 것인지 확인
        ReplyResDTO replyResDTO = replyDAO.selectReplierId(replyReqDTO.getReplyId());
        if(replyResDTO.getOrgUserId() !=orgUserId) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }
        int affectedCount = replyDAO.updateReply(replyReqDTO);
        if(affectedCount ==0) {
            throw new RestApiException(CustomErrorCode.REPLY_UPDATE_FAIL);
        }
        replyReqDTO.setApprovalDocId(replyResDTO.getApprovalDocId());
        this.createAlarm(replyReqDTO,"update");
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
        ReplyResDTO replyResDTO = replyDAO.selectReplierId(replyId);
        if(orgUserId ==replyResDTO.getOrgUserId()) {
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

    private void createAlarm(ReplyReqDTO replyReqDTO, String status) {
        //해당 문서의 상신자와 하위결재자에게 알림 보내기
        int approvalDocId = replyReqDTO.getApprovalDocId();
        int approverId = approveDAO.selectRecipientId(approvalDocId);
        List<Integer> approverIdList = approveDAO.selectApproverIdList(approvalDocId);

        if(status.equals("insert")) {
            alarmService.createNewAlarm(approvalDocId, approverId, AlarmStatus.REPLY.getCode());
            for (int approver : approverIdList) {
                alarmService.createNewAlarm(approvalDocId, approver, AlarmStatus.REPLY.getCode());
            }
        }else if(status.equals("update")) {
            alarmService.createNewAlarm(approvalDocId, approverId, AlarmStatus.UPDATE_REPLY.getCode());
            for (int approver : approverIdList) {
                alarmService.createNewAlarm(approvalDocId, approver, AlarmStatus.UPDATE_REPLY.getCode());
            }
        }
    }
}
