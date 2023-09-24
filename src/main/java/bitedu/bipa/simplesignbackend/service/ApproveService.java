package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.ApproveDAO;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalReqDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApproveService {

    private final ApproveDAO approveDAO;

    public ApproveService(ApproveDAO approveDAO) {
        this.approveDAO = approveDAO;
    }

    public void  registerApprovalDoc(ApprovalReqDTO approvalReqDTO, int userId) {
        //docStatus 가 상신인지 임시저장인지 확인

        //임시저장이면 approvalDoc, 첨부파일 만 insert
        approvalReqDTO.setCreatedAt(LocalDateTime.now());
        approvalReqDTO.setApprovalCount(3);
        int affectedCount = approveDAO.insertApprovalDoc(approvalReqDTO, userId);
        //상신이면 approvalDoc, approval,  alarm, 품의번호이력, 첨부파일 insert

    }
}
