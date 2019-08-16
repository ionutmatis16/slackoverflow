package ro.utcn.sd.mid.assign1.slackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerDeletedEvent extends BaseEvent {
    private final Integer answerDeletedId;

    public AnswerDeletedEvent(Answer answer) {
        super(EventType.ANSWER_DELETED);
        this.answerDeletedId = answer.getId();
    }
}
