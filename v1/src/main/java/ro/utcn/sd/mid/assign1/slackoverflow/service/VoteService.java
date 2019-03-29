package ro.utcn.sd.mid.assign1.slackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.AnswerVote;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.QuestionVote;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.AlreadyVotedException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.InvalidActionException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.QuestionNotFoundException;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.RepositoryFactory;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VoteService {
    private final RepositoryFactory rf;

    @Transactional
    public void voteQuestion(Integer questionId, Integer sOUserId, boolean value) {
        Optional<Question> question = rf.createQuestionRepository().findById(questionId);
        if (!question.isPresent()) {
            throw new QuestionNotFoundException();
        }
        if (question.get().getUserId().equals(sOUserId)) {
            throw new InvalidActionException("You cannot vote your own question.");
        }

        Optional<QuestionVote> qv = rf.createQuestionVoteRepository().
                findByQuestionSOUser(questionId, sOUserId);
        if (qv.isPresent()) {
            if (qv.get().getVoteType() == value) {
                throw new AlreadyVotedException();
            } else {
                qv.get().setVoteType(value);
                rf.createQuestionVoteRepository().save(qv.get());
            }
        } else {
            rf.createQuestionVoteRepository().save(new QuestionVote(questionId, sOUserId, value));
        }
    }

    @Transactional
    public void voteAnswer(Integer answerId, Integer sOUserId, boolean value) {
        Optional<Answer> answer = rf.createAnswerRepository().findById(answerId);
        if (!answer.isPresent()) {
            throw new QuestionNotFoundException();
        }
        if (answer.get().getUserId().equals(sOUserId)) {
            throw new InvalidActionException("You cannot vote your own answer.");
        }

        Optional<AnswerVote> av = rf.createAnswerVoteRepository().
                findByAnswerSOUser(answerId, sOUserId);
        if (av.isPresent()) {
            if (av.get().getVoteType() == value) {
                throw new AlreadyVotedException();
            } else {
                av.get().setVoteType(value);
                rf.createAnswerVoteRepository().save(av.get());
            }
        } else {
            rf.createAnswerVoteRepository().save(new AnswerVote(answerId, sOUserId, value));
        }
    }
}
