package ro.utcn.sd.mid.assign1.slackoverflow.repository.api;

import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.QuestionVote;

import java.util.Optional;

public interface QuestionVoteRepository extends AbstractRepository<QuestionVote> {
    int voteNr(Question question, boolean voteType);

    Optional<QuestionVote> findByQuestionSOUser(Integer questionId, Integer soUserId);
}
