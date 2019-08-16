package ro.utcn.sd.mid.assign1.slackoverflow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "questionVotes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVote implements IDEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer questionId;
    private Integer userId;
    @Column(columnDefinition = "TINYINT")
    private Boolean voteType;

    public QuestionVote(Integer questionID, Integer userID, Boolean voteType) {
        this.questionId = questionID;
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
