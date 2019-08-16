package ro.utcn.sd.mid.assign1.slackoverflow.dto;

import lombok.Data;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionDTO {
    private Integer id;
    private Integer userId;
    private String title;
    private String text;
    private Timestamp creationDate;
    private List<String> tags;
    private Integer score;

    public static QuestionDTO ofEntity(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setUserId(question.getUserId());
        dto.setTitle(question.getTitle());
        dto.setText(question.getText());
        dto.setCreationDate(question.getCreationDate());
        dto.setTags(question.getTags().stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList()));
        dto.setScore(question.getScore());
        return dto;
    }
}
