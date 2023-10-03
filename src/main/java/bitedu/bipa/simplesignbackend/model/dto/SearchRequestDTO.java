package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDTO {
    private List<String> viewItems;
    private int itemsPerPage;
    private int offset;
    private String searchDate;
    private Timestamp startDate;
    private Timestamp endDate;
    private String searchTitle;
    private String searchContent;
    private String searchDept;
    private String searchWriter;
    private String searchApprovUser;
    private String searchApprovState;
    private String searchDocForm;
    private String searchDocNumber;

}
