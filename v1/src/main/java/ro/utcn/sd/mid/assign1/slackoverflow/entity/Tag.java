package ro.utcn.sd.mid.assign1.slackoverflow.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Entity(name = "tags")
@NoArgsConstructor @AllArgsConstructor
public class Tag implements IDEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tagName;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private List<Question> taggedQuestions;

    public Tag(String tagName) {
        this.tagName = tagName;
        taggedQuestions = new ArrayList<>();
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
