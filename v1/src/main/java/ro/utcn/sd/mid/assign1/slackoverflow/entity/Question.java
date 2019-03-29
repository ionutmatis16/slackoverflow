package ro.utcn.sd.mid.assign1.slackoverflow.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "questions")
@AllArgsConstructor @NoArgsConstructor
public class Question implements IDEntity, Comparable<Question> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String title;
    private String text;
    private Timestamp creationDate;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "questionsTags",
            joinColumns = @JoinColumn(name = "questionId"),
            inverseJoinColumns = @JoinColumn(name = "tagId")
    )
    private List<Tag> tags;
    private Integer score;

    public Question(Integer userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        creationDate = new Timestamp(System.currentTimeMillis());
        tags = new ArrayList<>();
        score = 0;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getTaggedQuestions().add(this);
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
    public int compareTo(Question o) {
        return -this.getCreationDate().compareTo(o.getCreationDate());
    }

    @Override
    public String toString() {
        String res = "[" + score + "] Question(id=" + id +
                ", userId=" + userId +
                ", title=" + title +
                ", text=" + text +
                ", creationDate=" + creationDate.toString() +
                ", tags=[ ";
        String strTags = "";
        for(Tag t : tags) {
            strTags = strTags.concat(t.getTagName() + " ");
        }
        return res + strTags + "])";
    }
}
