package ro.utcn.sd.mid.assign1.slackoverflow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "sousers")
@NoArgsConstructor
@AllArgsConstructor
public class SOUser implements IDEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String sOUsername;
    private String sOPassword;
    private int score;

    public SOUser(String username, String password) {
        this.sOUsername = username;
        this.sOPassword = password;
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
}
