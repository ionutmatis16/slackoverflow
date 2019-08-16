package ro.utcn.sd.mid.assign1.slackoverflow.repository.jpa;

import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.QuestionVote;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.QuestionVoteRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class HibernateQuestionVoteRepository extends HibernateAbstractRepository<QuestionVote>
        implements QuestionVoteRepository {
    private final EntityManager entityManager;

    public HibernateQuestionVoteRepository(EntityManager entityManager) {
        super(entityManager, QuestionVote.class);
        this.entityManager = entityManager;
    }


    @Override
    @SuppressWarnings("Duplicates")
    public int voteNr(Question question, boolean voteType) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionVote> query = builder.createQuery(QuestionVote.class);
        Root<QuestionVote> from = query.from(QuestionVote.class);

        query.select(from);
        query.where(builder.and(builder.equal(from.get("questionId"), question.getId()), builder.equal(from.get("voteType"), voteType)));

        return entityManager.createQuery(query).getResultList().size();
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Optional<QuestionVote> findByQuestionSOUser(Integer questionId, Integer soUserId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionVote> query = builder.createQuery(QuestionVote.class);
        Root<QuestionVote> from = query.from(QuestionVote.class);

        query.select(from);
        query.where(builder.and(builder.equal(from.get("questionId"), questionId), builder.equal(from.get("userId"), soUserId)));

        List<QuestionVote> qvs = entityManager.createQuery(query).getResultList();
        return qvs.isEmpty() ? Optional.empty() : Optional.of(qvs.get(0));
    }
}
