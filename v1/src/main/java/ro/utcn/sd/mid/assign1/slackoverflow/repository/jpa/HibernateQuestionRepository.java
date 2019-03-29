package ro.utcn.sd.mid.assign1.slackoverflow.repository.jpa;

import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.QuestionRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class HibernateQuestionRepository extends HibernateAbstractRepository<Question>
        implements QuestionRepository {
    private final EntityManager entityManager;

    public HibernateQuestionRepository(EntityManager entityManager) {
        super(entityManager, Question.class);
        this.entityManager = entityManager;
    }

    @Override
    public List<Question> findByTitle(String title) {
        //We want question entities
        CriteriaQuery<Question> cq = entityManager.getCriteriaBuilder().createQuery(Question.class);
        Root<Question> from = cq.from(Question.class);

        cq.select(from);
        cq.where(entityManager.getCriteriaBuilder().like(from.get("title"),"%"+title+"%")); // <- this will add the restriction.


        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Tag> findTagsByQuestion(Question question) {
        return question.getTags();
    }

    @Override
    public List<Question> findSOUserQuestions(SOUser soUser) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Question> query = builder.createQuery(Question.class);
        Root<Question> from = query.from(Question.class);

        query.select(from);
        query.where(builder.equal(from.get("userId"), soUser.getId()));

        return entityManager.createQuery(query).getResultList();
    }
}
