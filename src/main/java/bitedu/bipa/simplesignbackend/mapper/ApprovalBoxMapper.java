package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.DocumentListDTO;
import bitedu.bipa.simplesignbackend.model.dto.SearchRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ApprovalBoxMapper {
    ArrayList<DocumentListDTO> getDocumentsByViewItems(
            @Param("userId") int userId,
            @Param("itemsPerPage") int itemsPerPage,
            @Param("offset") int offset,
            @Param("deptId") int deptId,
            @Param("estId") int estId,
            @Param("compId") int compId,
            @Param("viewItems") List<String> viewItems
    );

    ArrayList<DocumentListDTO> getDocCountByViewItems(@Param("userId") int userId,
                                                      @Param("deptId") int deptId,
                                                      @Param("estId") int estId,
                                                      @Param("compId") int compId,
                                                      @Param("viewItems") List<String> viewItems);

    ArrayList<DocumentListDTO> getSearchDocumentsByViewItems(@Param("userId") int userId,
                                                             @Param("itemsPerPage") int itemsPerPage,
                                                             @Param("offset") int offset,
                                                             @Param("deptId") int deptId,
                                                             @Param("viewItems") List<String> viewItems,
                                                             @Param("searchInput") String searchInput);

    ArrayList<DocumentListDTO> getSearchDocCountByViewItems(@Param("userId") int userId,
                                                            @Param("deptId") int deptId,
                                                            @Param("viewItems") List<String> viewItems,
                                                            @Param("searchInput") String searchInput);

    ArrayList<DocumentListDTO> getDetailSearchDocsList(@Param("userId") int userId,
                                                        @Param("deptId") int deptId,
                                                        @Param("viewItems") List<String> viewItems,
                                                        @Param("itemsPerPage") int itemsPerPage,
                                                        @Param("offset") int offset,
                                                        @Param("criteria") SearchRequestDTO criteria);

    ArrayList<DocumentListDTO> getDetailSearchDocsCount(@Param("userId") int userId,
                                                        @Param("deptId") int deptId,
                                                        @Param("viewItems") List<String> viewItems,
                                                        @Param("criteria") SearchRequestDTO criteria);

}