package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SeqManageMapper {
    List<SeqAndCompDTO> getSeqAndCompList(SeqAndCompDTO seqAndCompDTO);
}
