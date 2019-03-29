package ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.*;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "sd_assign1.repository-type", havingValue = "JDBC")
public class JdbcRepositoryFactory implements RepositoryFactory {
    private final JdbcAnswerRepository answerRepo;
    private final JdbcAnswerVoteRepository answerVoteRepo;
    private final JdbcQuestionRepository questionRepo;
    private final JdbcQuestionVoteRepository questionVoteRepo;
    private final JdbcSOUserRepository sOUserRepo;
    private final JdbcTagRepository tagRepo;

    @Override
    public AnswerRepository createAnswerRepository() {
        return answerRepo;
    }

    @Override
    public AnswerVoteRepository createAnswerVoteRepository() {
        return answerVoteRepo;
    }

    @Override
    public QuestionRepository createQuestionRepository() {
        return questionRepo;
    }

    @Override
    public QuestionVoteRepository createQuestionVoteRepository() {
        return questionVoteRepo;
    }

    @Override
    public SOUserRepository createSOUserRepository() {
        return sOUserRepo;
    }

    @Override
    public TagRepository createTagRepository() {
        return tagRepo;
    }
}
