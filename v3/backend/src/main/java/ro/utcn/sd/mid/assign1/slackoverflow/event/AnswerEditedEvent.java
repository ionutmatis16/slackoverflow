package ro.utcn.sd.mid.assign1.slackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.AnswerDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerEditedEvent extends BaseEvent {
    private final AnswerDTO answer;

    public AnswerEditedEvent(AnswerDTO answer) {
        super(EventType.ANSWER_EDITED);
        this.answer = answer;
    }
}
