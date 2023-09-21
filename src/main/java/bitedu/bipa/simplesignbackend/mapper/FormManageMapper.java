package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.CompanyDTO;
import bitedu.bipa.simplesignbackend.model.dto.FormAndCompDTO;
import bitedu.bipa.simplesignbackend.model.dto.FormDetailResDTO;
import bitedu.bipa.simplesignbackend.model.vo.FormDetailScopeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FormManageMapper {
    List<CompanyDTO> getFormAndCompList(FormAndCompDTO formAndCompDTO);
    FormDetailResDTO getFormDetail(int code);
    ArrayList<FormDetailScopeVO> getFormDetailScope(int code);
}
