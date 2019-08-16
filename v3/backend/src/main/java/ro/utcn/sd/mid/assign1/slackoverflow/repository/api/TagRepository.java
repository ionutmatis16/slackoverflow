package ro.utcn.sd.mid.assign1.slackoverflow.repository.api;

import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends AbstractRepository<Tag> {
    Optional<Tag> findByTagName(String tagName);

    List<Question> findQuestionsByTag(Tag tag);

    void addTagToQuestion(Tag tag, Question question);
}
