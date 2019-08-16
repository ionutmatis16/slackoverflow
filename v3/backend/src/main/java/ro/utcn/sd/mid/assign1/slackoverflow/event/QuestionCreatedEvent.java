package ro.utcn.sd.mid.assign1.slackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.QuestionDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionCreatedEvent extends BaseEvent {
    private final QuestionDTO question;

    public QuestionCreatedEvent(QuestionDTO question) {
        super(EventType.QUESTION_CREATED);
        this.question = question;
    }
}
