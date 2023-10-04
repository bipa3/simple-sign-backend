package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SeqDetailDTO {
    private String compId;
    private String code;
    private String seqName;
    private List<SeqScopeDTO> deptScope;
    private List<SeqScopeDTO> formScope;
    private String description;
    private int sortOrder;
    private String seqList;
    private String seqString;
}
