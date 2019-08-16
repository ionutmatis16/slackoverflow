package ro.utcn.sd.mid.assign1.slackoverflow.repository.api;

public interface RepositoryFactory {
    AnswerRepository createAnswerRepository();

    AnswerVoteRepository createAnswerVoteRepository();

    QuestionRepository createQuestionRepository();

    QuestionVoteRepository createQuestionVoteRepository();

    SOUserRepository createSOUserRepository();

    TagRepository createTagRepository();
}
