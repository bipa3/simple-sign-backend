package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.DocumentListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface ApprovalBoxMapper {
    ArrayList<DocumentListDTO> getSendDocList(@Param("userId") int userId);

    ArrayList<DocumentListDTO> getTemporDocList(@Param("userId")int userId);

    ArrayList<DocumentListDTO> getPendDocList(@Param("userId")int userId);

    ArrayList<DocumentListDTO> getConcludeDocList(@Param("userId")int userId);

    ArrayList<DocumentListDTO> getReferenceDocList(@Param("userId") int userId, @Param("deptId") int deptId);

}
