package ro.utcn.sd.mid.assign1.slackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.QuestionVote;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVoteDTO {
    private Integer id;
    private Integer userId;
    private Boolean voteType;

    public static QuestionVoteDTO ofEntity(QuestionVote qV) {
        QuestionVoteDTO dto = new QuestionVoteDTO();
        dto.setId(qV.getId());
        dto.setUserId(qV.getUserId());
        dto.setVoteType(qV.getVoteType());
        return dto;
    }
}
