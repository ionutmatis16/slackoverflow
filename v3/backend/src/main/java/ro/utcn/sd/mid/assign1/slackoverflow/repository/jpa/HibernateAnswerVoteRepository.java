package ro.utcn.sd.mid.assign1.slackoverflow.repository.jpa;

import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.AnswerVote;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.AnswerVoteRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


public class HibernateAnswerVoteRepository extends HibernateAbstractRepository<AnswerVote>
        implements AnswerVoteRepository {
    private final EntityManager entityManager;

    public HibernateAnswerVoteRepository(EntityManager entityManager) {
        super(entityManager, AnswerVote.class);
        this.entityManager = entityManager;
    }

    @Override
    public int voteNr(Answer answer, boolean voteType) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AnswerVote> query = builder.createQuery(AnswerVote.class);
        Root<AnswerVote> from = query.from(AnswerVote.class);

        query.select(from);
        query.where(builder.and(builder.equal(from.get("answerId"), answer.getId()),
                builder.equal(from.get("voteType"), voteType)));

        return entityManager.createQuery(query).getResultList().size();
    }


    @Override
    public Optional<AnswerVote> findByAnswerSOUser(Integer answerId, Integer soUserId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AnswerVote> query = builder.createQuery(AnswerVote.class);
        Root<AnswerVote> from = query.from(AnswerVote.class);

        query.select(query.from(AnswerVote.class));
        query.where(builder.equal(from.get("answerId"), answerId));
        query.where(builder.equal(from.get("userId"), soUserId));

        List<AnswerVote> av = entityManager.createQuery(query).getResultList();

        return av.isEmpty() ? Optional.empty() : Optional.of(av.get(0));
    }
}
