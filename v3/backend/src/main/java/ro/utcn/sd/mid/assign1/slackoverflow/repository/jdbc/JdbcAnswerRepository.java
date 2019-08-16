package ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.AnswerRepository;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc.mapper.AnswerMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JdbcAnswerRepository implements AnswerRepository {
    private final JdbcTemplate template;

    @Override
    public Answer save(Answer answer) {
        if (answer.getId() == null) {
            answer.setId(insert(answer));
        } else {
            update(answer);
        }
        return answer;
    }

    @Override
    public Optional<Answer> findById(Integer id) {
        List<Answer> answers = template.query("SELECT * FROM answers WHERE id = ?",
                new AnswerMapper(), id);
        return answers.isEmpty() ? Optional.empty() : Optional.of(answers.get(0));
    }

    @Override
    public void delete(Answer answer) {
        template.update("DELETE FROM answers WHERE id = ?",
                answer.getId());
    }

    @Override
    public List<Answer> findAll() {
        return template.query("SELECT * FROM answers",
                new AnswerMapper());
    }

    @SuppressWarnings("Duplicates")
    private Integer insert(Answer answer) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("answers");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("questionId", answer.getQuestionId());
        map.put("userId", answer.getUserId());
        map.put("text", answer.getText());
        map.put("creationDate", answer.getCreationDate());
        map.put("score", answer.getScore());
        return insert.executeAndReturnKey(map).intValue();
    }

    private void update(Answer answer) {
        template.update("UPDATE answers SET questionId = ?, userId = ?, text = ?, creationDate = ?, score = ? " +
                        "WHERE id = ?", answer.getQuestionId(),
                answer.getUserId(),
                answer.getText(),
                answer.getCreationDate(),
                answer.getScore(),
                answer.getId());
    }

    @Override
    public List<Answer> listAnswersForQuestion(Question question) {
        return template.query("SELECT * FROM answers WHERE questionid = ?",
                new AnswerMapper(), question.getId());
    }

    @Override
    public List<Answer> findSOUserAnswers(SOUser soUser) {
        return template.query("SELECT * FROM answers WHERE userId = ?",
                new AnswerMapper(), soUser.getId());
    }
}
