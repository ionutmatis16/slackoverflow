package ro.utcn.sd.mid.assign1.slackoverflow.repository.memory;

import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryTagRepository extends InMemoryAbstractRepository<Tag>
        implements TagRepository {
    private Map<Integer, Tag> data = super.getData();

    @Override
    public Optional<Tag> findByTagName(String tagName) {
        for (Tag t : data.values()) {
            if (t.getTagName().equals(tagName))
                return Optional.of(t);
        }
        return Optional.empty();
    }

    @Override
    public List<Question> findQuestionsByTag(Tag tag) {
        for(Tag t : data.values()) {
            if (t.getId().equals(tag.getId())) {
                return new ArrayList<>(t.getTaggedQuestions());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void addTagToQuestion(Tag tag, Question question) {
        question.addTag(tag);
    }
}
