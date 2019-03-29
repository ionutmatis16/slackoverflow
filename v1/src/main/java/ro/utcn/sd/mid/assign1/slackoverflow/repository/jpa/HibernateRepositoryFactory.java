package ro.utcn.sd.mid.assign1.slackoverflow.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.*;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "sd_assign1.repository-type", havingValue = "JPA")
public class HibernateRepositoryFactory implements RepositoryFactory {
    private final EntityManager entityManager;

    @Override
    public AnswerRepository createAnswerRepository() {
        return new HibernateAnswerRepository(entityManager);
    }

    @Override
    public AnswerVoteRepository createAnswerVoteRepository() {
        return new HibernateAnswerVoteRepository(entityManager);
    }

    @Override
    public QuestionRepository createQuestionRepository() {
        return new HibernateQuestionRepository(entityManager);
    }

    @Override
    public QuestionVoteRepository createQuestionVoteRepository() {
        return new HibernateQuestionVoteRepository(entityManager);
    }

    @Override
    public SOUserRepository createSOUserRepository() {
        return new HibernateSOUserRepository(entityManager);
    }

    @Override
    public TagRepository createTagRepository() {
        return new HibernateTagRepository(entityManager);
    }
}
