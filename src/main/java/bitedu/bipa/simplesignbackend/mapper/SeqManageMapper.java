package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqScopeDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface SeqManageMapper {
    List<SeqAndCompDTO> getSeqAndCompList(SeqAndCompDTO seqAndCompDTO);
    SeqDetailDTO getSeqDetail(int code);
    List<SeqScopeDTO> getSeqDeptScope(int code);
    List<SeqScopeDTO> getSeqFormScope(int code);
    void deleteSeq(int code);

    void createSeqDetail(SeqDetailDTO seqDetail);
    void createSeqDeptScope(Map map);
    void createSeqFormScope(Map map);

    String selectSeqItems(Map map);
}
