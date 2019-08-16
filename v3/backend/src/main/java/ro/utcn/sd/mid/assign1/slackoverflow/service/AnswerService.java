package ro.utcn.sd.mid.assign1.slackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.AnswerDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;
import ro.utcn.sd.mid.assign1.slackoverflow.event.AnswerCreatedEvent;
import ro.utcn.sd.mid.assign1.slackoverflow.event.AnswerDeletedEvent;
import ro.utcn.sd.mid.assign1.slackoverflow.event.AnswerEditedEvent;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.AnswerNotFoundException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.InvalidActionException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.QuestionNotFoundException;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.RepositoryFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final RepositoryFactory repositoryFactory;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public List<AnswerDTO> listAllAnswers() {
        return repositoryFactory.createAnswerRepository().findAll()
                .stream()
                .map(AnswerDTO::ofEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AnswerDTO> listAnswersForQuestion(Integer questionId) {
        Optional<Question> foundQuestion = repositoryFactory.createQuestionRepository().findById(questionId);
        if (foundQuestion.isPresent()) {
            List<Answer> answers = repositoryFactory.createAnswerRepository().listAnswersForQuestion(foundQuestion.get());
            for (Answer a : answers) {
                int score = repositoryFactory.createAnswerVoteRepository().voteNr(a, true) -
                        repositoryFactory.createAnswerVoteRepository().voteNr(a, false);
                a.setScore(score);
            }
            answers.sort((o1, o2) -> -o1.getScore().compareTo(o2.getScore()));
            return answers.stream()
                    .map(AnswerDTO::ofEntity)
                    .collect(Collectors.toList());
        } else
            throw new QuestionNotFoundException();

    }

    @Transactional
    public AnswerDTO saveAnswer(AnswerDTO dto, Integer questionId) {
        Answer answer = new Answer(questionId,
                dto.getUserId(),
                dto.getText());

        Optional<Question> question = repositoryFactory.createQuestionRepository().findById(answer.getQuestionId());
        if (question.isPresent()) {
            AnswerDTO output = AnswerDTO.ofEntity(repositoryFactory.createAnswerRepository().save(answer));
            eventPublisher.publishEvent(new AnswerCreatedEvent(output));
            return output;
        } else {
            throw new QuestionNotFoundException();
        }
    }

    @Transactional
    public AnswerDTO editAnswer(Integer answerId, Integer questionId, String soUserName, String answerText) {
        Optional<Answer> answer = repositoryFactory.createAnswerRepository().findById(answerId);
        if (answer.isPresent()) {
            Optional<SOUser> user = repositoryFactory.createSOUserRepository().findBySOUsername(soUserName);
            if (user.isPresent() && answer.get().getUserId().equals(user.get().getId())) {
                answer.get().setText(answerText);
                AnswerDTO output = AnswerDTO.ofEntity(repositoryFactory.createAnswerRepository().save(answer.get()));
                eventPublisher.publishEvent(new AnswerEditedEvent(output));
                return output;
            } else {
                throw new InvalidActionException("You can only edit your answer.");
            }
        } else {
            throw new AnswerNotFoundException();
        }
    }

    @Transactional
    public AnswerDTO findById(Integer answerId) {
        Optional<Answer> answer = repositoryFactory.createAnswerRepository().findById(answerId);
        if (answer.isPresent()) {
            int score = repositoryFactory.createAnswerVoteRepository().voteNr(answer.get(), true) -
                    repositoryFactory.createAnswerVoteRepository().voteNr(answer.get(), false);
            answer.get().setScore(score);
            return AnswerDTO.ofEntity(answer.get());
        } else
            throw new AnswerNotFoundException();
    }

    @Transactional
    public Integer deleteAnswer(Integer answerId, Integer questionId, String soUserName) {
        Optional<Answer> answer = repositoryFactory.createAnswerRepository().findById(answerId);
        if (answer.isPresent()) {
            Optional<SOUser> user = repositoryFactory.createSOUserRepository().findBySOUsername(soUserName);
            if (user.isPresent() && answer.get().getUserId().equals(user.get().getId())) {
                repositoryFactory.createAnswerRepository().delete(answer.get());
                eventPublisher.publishEvent(new AnswerDeletedEvent(answer.get()));
                return answer.get().getId();
            } else {
                throw new InvalidActionException("You can only delete your answer.");
            }
        } else {
            throw new AnswerNotFoundException();
        }
    }

}
