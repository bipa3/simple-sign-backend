package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.enums.ApprovalStatus;
import bitedu.bipa.simplesignbackend.mapper.ApproveMapper;
import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.mapper.ReplyMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReplyDAO {

    private final ReplyMapper replyMapper;

    public ReplyDAO(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
    }

    public List<ReplyResDTO> selectAllReplyList(int approvalDocId) {
        return replyMapper.selectAllReplyList(approvalDocId);
    }

    public void insertReply(ReplyReqDTO replyReqDTO) {

       int affectedCount = replyMapper.insertReply(replyReqDTO);
       if(affectedCount ==0) {
           throw  new RuntimeException(); //댓글 삽입 실패
       }
       int replyId = replyReqDTO.getReplyId();
        System.out.println("replyId"+ replyId);
       //그룹의 순서 가져오기
        int depth = 1;
        Map<String, Integer> map = new HashMap();
        map.put("depth", depth);
        map.put("approvalDocId", replyReqDTO.getApprovalDocId());
        int groupOrd = replyMapper.selectGroupOrd(map);
        System.out.println(groupOrd);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("replyId", replyId);
        map2.put("groupOrd", groupOrd);
        affectedCount = replyMapper.updateGroupNoAndOrder(map2);
        System.out.println(affectedCount);
       if(affectedCount ==0) {
           throw new RuntimeException(); //댓글 업데이트 실패
       }
    }


    @Transactional
    public void insertLowerReply(ReplyReqDTO replyReqDTO) {
        int affectedCount = replyMapper.insertLowerReply(replyReqDTO);
        if(affectedCount ==0) {
            throw new RuntimeException(); //대댓글 삽입 실패
        }
        int replyId = replyReqDTO.getReplyId();
        int depth = 2;
        Map<String, Integer> map = new HashMap();
        map.put("depth", depth);
        map.put("approvalDocId", replyReqDTO.getApprovalDocId());
        int groupOrd = replyMapper.selectGroupOrd(map);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("replyId", replyId);
        map2.put("groupOrd", groupOrd);
        affectedCount = replyMapper.updateGroupOrder(map2);
        if(affectedCount ==0) {
            throw new RuntimeException(); //댓글 업데이트 실패
        }


    }
}
