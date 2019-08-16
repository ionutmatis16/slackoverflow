package ro.utcn.sd.mid.assign1.slackoverflow.dto;

import lombok.Data;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;

@Data
public class EditAnswerDTO {
    private String newAnswerText;

    public static EditAnswerDTO ofEntity(Answer answer) {
        EditAnswerDTO dto = new EditAnswerDTO();
        dto.setNewAnswerText(answer.getText());
        return dto;
    }
}
