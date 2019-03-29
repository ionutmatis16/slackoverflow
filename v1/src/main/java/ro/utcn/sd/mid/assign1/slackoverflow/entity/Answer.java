package ro.utcn.sd.mid.assign1.slackoverflow.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "answers")
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class Answer implements IDEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer questionId;
    private Integer userId;
    private String text;
    private Timestamp creationDate;
    private Integer score;

    public Answer(Integer questionID, Integer userID, String text) {
        this.questionId = questionID;
        this.userId = userID;
        this.text = text;
        creationDate = new Timestamp(System.currentTimeMillis());
        score = 0;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[" + score + "] Answer(" +
                "id=" + id +
                ", questionId=" + questionId +
                ", userId=" + userId +
                ", text=" + text + ")";
    }
}
