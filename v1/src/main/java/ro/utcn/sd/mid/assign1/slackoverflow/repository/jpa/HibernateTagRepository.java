package ro.utcn.sd.mid.assign1.slackoverflow.repository.jpa;

import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.TagRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class HibernateTagRepository extends HibernateAbstractRepository<Tag>
implements TagRepository {
    private final EntityManager entityManager;

    public HibernateTagRepository(EntityManager entityManager) {
        super(entityManager, Tag.class);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Tag> findByTagName(String tagName) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        Root<Tag> from = query.from(Tag.class);

        query.select(from);
        query.where(builder.equal(from.get("tagName"),tagName)); // <- this will add the restriction.

        List<Tag> foundTags = entityManager.createQuery(query).getResultList();
        return foundTags.isEmpty() ? Optional.empty() : Optional.of(foundTags.get(0));
    }

    @Override
    public List<Question> findQuestionsByTag(Tag tag) {
        return tag.getTaggedQuestions();
    }

    @Override
    public void addTagToQuestion(Tag tag, Question q) {
        q.addTag(tag);
    }
}
