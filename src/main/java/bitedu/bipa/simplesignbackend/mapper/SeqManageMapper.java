package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqScopeDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SeqManageMapper {
    List<SeqAndCompDTO> getSeqAndCompList(SeqAndCompDTO seqAndCompDTO);
    SeqDetailDTO getSeqDetail(int code);
    List<SeqScopeDTO> getSeqDeptScope(int code);
    List<SeqScopeDTO> getSeqFormScope(int code);
}
