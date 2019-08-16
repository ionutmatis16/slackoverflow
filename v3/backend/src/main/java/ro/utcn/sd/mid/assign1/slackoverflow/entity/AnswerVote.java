package ro.utcn.sd.mid.assign1.slackoverflow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "answerVotes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVote implements IDEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer answerId;
    private Integer userId;
    @Column(columnDefinition = "TINYINT")
    private Boolean voteType;

    public AnswerVote(Integer answerID, Integer userID, Boolean voteType) {
        this.answerId = answerID;
        this.userId = userID;
        this.voteType = voteType;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
