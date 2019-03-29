package ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.TagRepository;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc.mapper.QuestionMapper;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc.mapper.TagMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JdbcTagRepository implements TagRepository {
    private final JdbcTemplate template;

    @Override
    public Optional<Tag> findByTagName(String tagName) {
        List<Tag> tags = template.query("SELECT * FROM tags WHERE tagName = ?",
                new TagMapper(), tagName);
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }

    @Override
    public Tag save(Tag tag) {
        if (tag.getId() == null) {
            tag.setId(insert(tag));
        } else {
            update(tag);
        }
        return tag;
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        List<Tag> tags = template.query("SELECT * FROM tags WHERE id = ?",
                new TagMapper(), id);
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));

    }

    @Override
    public void delete(Tag tag) {
        template.update("DELETE FROM tags WEHRE id = ?", tag.getId());
    }

    @Override
    public List<Tag> findAll() {
        return template.query("SELECT * FROM tags", new TagMapper());
    }

    private int insert(Tag tag) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("tags");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("tagName", tag.getTagName());
        return insert.executeAndReturnKey(map).intValue();
    }

    private void update(Tag tag) {
        template.update("UPDATE tags SET tagName = ? WHERE id = ?",
                tag.getTagName(), tag.getId());
    }

    @Override
    public List<Question> findQuestionsByTag(Tag tag) {
        return template.query("select questions.* from questions join questionstags on " +
                "questions.id = questionstags.questionid where tagid = ?", new QuestionMapper(), tag.getId());
    }

    @Override
    public void addTagToQuestion(Tag tag, Question question) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("questionsTags");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("questionId", question.getId());
        map.put("tagId", tag.getId());
        insert.executeAndReturnKey(map).intValue();
    }
}
