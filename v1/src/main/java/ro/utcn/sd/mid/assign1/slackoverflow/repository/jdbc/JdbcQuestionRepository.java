package ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.QuestionRepository;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc.mapper.QuestionMapper;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc.mapper.TagMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JdbcQuestionRepository implements QuestionRepository {
    private final JdbcTemplate template;

    @Override
    public Question save(Question question) {
        if (question.getId() == null) {
            question.setId(insert(question));
        } else {
            update(question);
        }
        return question;
    }

    @Override
    public Optional<Question> findById(Integer id) {
        List<Question> questions = template.query("SELECT * FROM questions WHERE id = ?",
                new QuestionMapper(), id);
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    @Override
    public void delete(Question question) {
        template.update("DELETE FROM questions WEHRE id = ?", question.getId());
    }

    @Override
    public List<Question> findAll() {
        return template.query("SELECT * FROM questions", new QuestionMapper());
    }


    private int insert(Question question) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("questions");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", question.getUserId());
        map.put("title", question.getTitle());
        map.put("text", question.getText());
        map.put("creationDate", question.getCreationDate());
        map.put("score", question.getScore());
        return insert.executeAndReturnKey(map).intValue();
    }

    private void update(Question question) {
        template.update("UPDATE questions SET userId = ?, title = ?, text = ?, " +
                        "creationDate = ?, score = ? WHERE id = ?",
                question.getUserId(), question.getTitle(), question.getText(),
                question.getCreationDate(), question.getScore(), question.getId());
    }

    @Override
    public List<Question> findByTitle(String title) {
        return template.query("SELECT * FROM questions WHERE title like ?", new QuestionMapper(),
                "%" + title + "%");
    }

    @Override
    public List<Tag> findTagsByQuestion(Question question) {
        return template.query("select tags.* from tags join questionstags on " +
                "tags.id = questionstags.tagid where questionid = ?", new TagMapper(), question.getId());

    }

    @Override
    public List<Question> findSOUserQuestions(SOUser soUser) {
        return template.query("select * from questions where userId = ?", new QuestionMapper(), soUser.getId());
    }
}
