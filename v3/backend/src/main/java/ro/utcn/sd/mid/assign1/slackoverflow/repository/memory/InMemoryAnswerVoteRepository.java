package ro.utcn.sd.mid.assign1.slackoverflow.repository.memory;

import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.AnswerVote;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.AnswerVoteRepository;

import java.util.Optional;

public class InMemoryAnswerVoteRepository extends InMemoryAbstractRepository<AnswerVote>
        implements AnswerVoteRepository {
    @Override
    public int voteNr(Answer answer, boolean voteType) {
        int nr = 0;
        for (AnswerVote av : getData().values()) {
            if (av.getAnswerId().equals(answer.getId()) && (av.getVoteType() == voteType)) {
                nr++;
            }
        }
        return nr;
    }

    @Override
    public Optional<AnswerVote> findByAnswerSOUser(Integer answerId, Integer soUserId) {
        for (AnswerVote answerVote : getData().values()) {
            if (answerVote.getAnswerId().equals(answerId)
                    && answerVote.getUserId().equals(soUserId))
                return Optional.of(answerVote);
        }
        return Optional.empty();
    }
}
