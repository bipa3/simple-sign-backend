package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.DocumentListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ApprovalBoxMapper {
    ArrayList<DocumentListDTO> getSendDocList(@Param("userId") int userId, @Param("itemsPerPage")int itemsPerPage, @Param("offset")int offset);

    ArrayList<DocumentListDTO> getTemporDocList(@Param("userId") int userId, @Param("itemsPerPage")int itemsPerPage, @Param("offset")int offset);

    ArrayList<DocumentListDTO> getPendDocList(@Param("userId") int userId, @Param("itemsPerPage")int itemsPerPage, @Param("offset")int offset);

    ArrayList<DocumentListDTO> getConcludeDocList(@Param("userId") int userId, @Param("itemsPerPage")int itemsPerPage, @Param("offset")int offset);

    ArrayList<DocumentListDTO> getReferenceDocList(@Param("userId") int userId, @Param("itemsPerPage")int itemsPerPage, @Param("offset")int offset, @Param("deptId") int deptId);

}
