package ro.utcn.sd.mid.assign1.slackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.AnswerVoteDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerVotedEvent extends BaseEvent {
    private final AnswerVoteDTO dto;

    public AnswerVotedEvent(AnswerVoteDTO dto) {
        super(EventType.ANSWER_VOTED);
        this.dto = dto;
    }
}
