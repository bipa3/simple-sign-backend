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
            @Param("orgUserId") int orgUserId,
            @Param("itemsPerPage") int itemsPerPage,
            @Param("offset") int offset,
            @Param("deptId") int deptId,
            @Param("estId") int estId,
            @Param("compId") int compId,
            @Param("viewItems") List<String> viewItems,
            @Param("sortStatus") String sortStatus
    );

    int getDocCountByViewItems(@Param("orgUserId") int orgUserId,
                                                      @Param("deptId") int deptId,
                                                      @Param("estId") int estId,
                                                      @Param("compId") int compId,
                                                      @Param("viewItems") List<String> viewItems);

    ArrayList<DocumentListDTO> getSearchDocumentsByViewItems(@Param("orgUserId") int orgUserId,
                                                             @Param("itemsPerPage") int itemsPerPage,
                                                             @Param("offset") int offset,
                                                             @Param("deptId") int deptId,
                                                             @Param("estId") int estId,
                                                             @Param("compId") int compId,
                                                             @Param("viewItems") List<String> viewItems,
                                                             @Param("searchInput") String searchInput,
                                                            @Param("sortStatus") String sortStatus);

    int getSearchDocCountByViewItems(@Param("orgUserId") int orgUserId,
                                                            @Param("deptId") int deptId,
                                                            @Param("estId") int estId,
                                                            @Param("compId") int compId,
                                                            @Param("viewItems") List<String> viewItems,
                                                            @Param("searchInput") String searchInput);

    ArrayList<DocumentListDTO> getDetailSearchDocsList(@Param("orgUserId") int orgUserId,
                                                        @Param("deptId") int deptId,
                                                       @Param("estId") int estId,
                                                       @Param("compId") int compId,
                                                        @Param("viewItems") List<String> viewItems,
                                                        @Param("itemsPerPage") int itemsPerPage,
                                                        @Param("offset") int offset,
                                                        @Param("criteria") SearchRequestDTO criteria);

    int getDetailSearchDocsCount(@Param("orgUserId") int orgUserId,
                                                        @Param("deptId") int deptId,
                                                        @Param("estId") int estId,
                                                        @Param("compId") int compId,
                                                        @Param("viewItems") List<String> viewItems,
                                                        @Param("criteria") SearchRequestDTO criteria);

    int selectSendCount(@Param("orgUserId") int orgUserId);

    int selectPendCount(@Param("orgUserId") int orgUserId);

    int selectConcludedCount(@Param("orgUserId") int orgUserId);

    int selectReferenceCount(@Param("orgUserId") int orgUserId,
                             @Param("deptId") int deptId,
                             @Param("estId") int estId,
                             @Param("compId") int compId);

    void insertDocView(@Param("orgUserId") int userId, @Param("docId") int docId);

    ArrayList<Integer> selectDocView(@Param("orgUserId") int orgUserId);
}