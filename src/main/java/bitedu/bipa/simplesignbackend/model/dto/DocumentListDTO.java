package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentListDTO {
    private int approvalDocId;
    private String approvalDocTitle;
    private char docStatus;
    private Date createdAt;
    private int userId;
    private String userName;
    private int deptId;
    private String deptName;
    private int docDelStatus;
    private String formName;
}
