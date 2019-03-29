package ro.utcn.sd.mid.assign1.slackoverflow.repository;

import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.AnswerVote;

import java.util.Optional;

public interface AnswerVoteRepository extends AbstractRepository<AnswerVote> {
    int voteNr(Answer answer, boolean voteType);
    Optional<AnswerVote> findByAnswerSOUser(Integer answerId, Integer soUserId);
}
