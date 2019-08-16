package ro.utcn.sd.mid.assign1.slackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.AnswerVote;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVoteDTO {
    private Integer id;
    private Integer userId;
    private Boolean voteType;

    public static AnswerVoteDTO ofEntity(AnswerVote aV) {
        AnswerVoteDTO dto = new AnswerVoteDTO();
        dto.setId(aV.getId());
        dto.setUserId(aV.getUserId());
        dto.setVoteType(aV.getVoteType());
        return dto;
    }
}
