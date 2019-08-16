package ro.utcn.sd.mid.assign1.slackoverflow.dto;

import lombok.Data;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;

import java.sql.Timestamp;

@Data
public class AnswerDTO {
    private Integer id;
    private Integer userId;
    private String text;
    private Timestamp creationDate;
    private Integer score;

    public static AnswerDTO ofEntity(Answer answer) {
        AnswerDTO dto = new AnswerDTO();
        dto.setId(answer.getId());
        dto.setUserId(answer.getUserId());
        dto.setText(answer.getText());
        dto.setCreationDate(answer.getCreationDate());
        dto.setScore(answer.getScore());
        return dto;
    }
}
