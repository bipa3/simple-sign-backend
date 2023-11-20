package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.model.dto.FormDetailScopeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface FormManageMapper {
    List<CompanyDTO> getFormAndCompList(FormAndCompDTO formAndCompDTO);
    FormDetailResDTO getFormDetail(int code);
    ArrayList<FormDetailScopeDTO> getFormDetailScope(int code);

    List<FormListDTO> selectFormListWithSearch(Map map);

    List<SequenceListDTO> selectSequence(Map map);

    SequenceListDTO selectSequenceByUseId(Map map);

    List<SequenceListDTO> selectSequence();

    List<FormItemDTO> getFormItemList();
    void createFormDetail(FormDetailResDTO formDetail);

    void createFormScope(Map map);

    void updateFormDetail(FormDetailResDTO formDetail);

    void updateFormScope(Map map);

    void delFormScope(Map map);

    void insertIgnoreFormScope(Map map);

    void deleteForm(int code);

    List<FormDTO> selectFormListAll();

    List<DefaultApprovalLineDTO> selectDefaultApprovalLine(int code);
    void createDefaultApprovalLine(Map map);

    void delDefaultLine(Map map);
    void insertIgnoreDefaultLine(Map map);

    List<FormDTO> getFormByCompId(int compId);

}
