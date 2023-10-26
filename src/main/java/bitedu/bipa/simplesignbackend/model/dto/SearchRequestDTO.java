package bitedu.bipa.simplesignbackend.model.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PastOrPresent;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDTO {
    private List<String> viewItems;
    private int itemsPerPage;
    private int offset;
    private String searchDate;
    @PastOrPresent(message = "기간이 유효하지 않습니다.")
    @JsonDeserialize(using = IsoToTimestampDeserializer.class)
    private Timestamp startDate;
    @PastOrPresent(message = "기간이 유효하지 않습니다.")
    @JsonDeserialize(using = IsoToTimestampDeserializer.class)
    private Timestamp endDate;
    private String searchTitle;
    private String searchContent;
    private String searchDept;
    private String searchWriter;
    private String searchApprovUser;
    private String searchApprovState;
    private String searchDocForm;
    private String searchDocNumber;

    public static class IsoToTimestampDeserializer extends JsonDeserializer<Timestamp> {
        @Override
        public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            try {
                Instant instant = Instant.parse(p.getText());
                return Timestamp.from(instant);
            } catch (DateTimeParseException e) {
                throw new IOException("Invalid date format", e);
            }
        }
    }
}
