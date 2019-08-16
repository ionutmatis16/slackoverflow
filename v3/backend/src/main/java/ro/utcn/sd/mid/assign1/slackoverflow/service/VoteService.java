package ro.utcn.sd.mid.assign1.slackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.AnswerVoteDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.QuestionVoteDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.AnswerVote;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.QuestionVote;
import ro.utcn.sd.mid.assign1.slackoverflow.event.AnswerVotedEvent;
import ro.utcn.sd.mid.assign1.slackoverflow.event.QuestionVotedEvent;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.AlreadyVotedException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.InvalidActionException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.QuestionNotFoundException;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.RepositoryFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VoteService {
    private final RepositoryFactory rf;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public QuestionVoteDTO voteQuestion(Integer questionId, QuestionVoteDTO dto) {
        Integer sOUserId = dto.getUserId();
        Boolean value = dto.getVoteType();
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
                QuestionVoteDTO output = QuestionVoteDTO.ofEntity(rf.createQuestionVoteRepository().save(qv.get()));
                eventPublisher.publishEvent(new QuestionVotedEvent(output));
                return output;
            }
        } else {
            QuestionVoteDTO output = QuestionVoteDTO.ofEntity(rf.createQuestionVoteRepository()
                    .save(new QuestionVote(questionId, sOUserId, value)));
            eventPublisher.publishEvent(new QuestionVotedEvent(output));
            return output;
        }
    }

    @Transactional
    public AnswerVoteDTO voteAnswer(Integer questionId, Integer answerId, AnswerVoteDTO dto) {
        Integer sOUserId = dto.getUserId();
        boolean value = dto.getVoteType();

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
                AnswerVoteDTO output = AnswerVoteDTO.ofEntity(rf.createAnswerVoteRepository().save(av.get()));
                eventPublisher.publishEvent(new AnswerVotedEvent(output));
                return output;
            }
        } else {
            AnswerVoteDTO output = AnswerVoteDTO.ofEntity(rf.createAnswerVoteRepository()
                    .save(new AnswerVote(answerId, sOUserId, value)));
            eventPublisher.publishEvent(new AnswerVotedEvent(output));
            return output;
        }
    }

    @Transactional
    public List<QuestionVoteDTO> findAllQuestionVotes() {
        return rf.createQuestionVoteRepository().findAll().stream()
                .map(QuestionVoteDTO::ofEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AnswerVoteDTO> findAllAnswerVotes() {
        return rf.createAnswerVoteRepository().findAll().stream()
                .map(AnswerVoteDTO::ofEntity)
                .collect(Collectors.toList());
    }
}
