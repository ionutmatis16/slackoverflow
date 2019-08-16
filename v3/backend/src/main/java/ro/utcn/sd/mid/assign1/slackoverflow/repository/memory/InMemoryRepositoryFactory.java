package ro.utcn.sd.mid.assign1.slackoverflow.repository.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.*;

@Component
@ConditionalOnProperty(name = "sd_assign1.repository-type", havingValue = "MEMORY")
public class InMemoryRepositoryFactory implements RepositoryFactory {
    private final InMemoryAnswerRepository answerRepo = new InMemoryAnswerRepository();
    private final InMemoryAnswerVoteRepository answerVoteRepo = new InMemoryAnswerVoteRepository();
    private final InMemoryQuestionRepository questionRepo = new InMemoryQuestionRepository();
    private final InMemoryQuestionVoteRepository questionVoteRepo = new InMemoryQuestionVoteRepository();
    private final InMemorySOUserRepository userRepo = new InMemorySOUserRepository();
    private final InMemoryTagRepository tagRepo = new InMemoryTagRepository();

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
        return userRepo;
    }

    @Override
    public TagRepository createTagRepository() {
        return tagRepo;
    }
}
