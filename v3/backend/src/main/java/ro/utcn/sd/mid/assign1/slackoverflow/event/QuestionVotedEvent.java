package ro.utcn.sd.mid.assign1.slackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.QuestionVoteDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionVotedEvent extends BaseEvent {
    private final QuestionVoteDTO dto;

    public QuestionVotedEvent(QuestionVoteDTO dto) {
        super(EventType.QUESTION_VOTED);
        this.dto = dto;
    }
}
