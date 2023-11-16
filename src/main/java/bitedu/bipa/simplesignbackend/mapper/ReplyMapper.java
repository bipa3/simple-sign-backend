package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface ReplyMapper {

    List<ReplyResDTO> selectAllReplyList(int approvalDocId);

    int insertReply(ReplyReqDTO replyReqDTO);

    int selectGroupOrd(Map map);

    int updateGroupNoAndOrder(Map map);

    int insertLowerReply(ReplyReqDTO replyReqDTO);

    int updateGroupOrder(Map map);

    int updateReply(ReplyReqDTO replyReqDTO);

    int deleteReply(int replyId);
    
    ReplyResDTO selectReplierId(int replyId);

    ReplyResDTO selectReplierAndDepth(int replyId);

    int deleteLowerReply(int replyId);

    int insertReplyAttachment(ReplyAttachmentDTO replyAttachmentDTO);

    List<FileResDTO> selectFileNamesAndFilePath(int replyId);
}
