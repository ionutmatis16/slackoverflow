package ro.utcn.sd.mid.assign1.slackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.AnswerNotFoundException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.InvalidActionException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.QuestionNotFoundException;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.RepositoryFactory;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final RepositoryFactory repositoryFactory;

    @Transactional
    public List<Answer> listAnswersForQuestion(Question question) {
        List<Answer> answers = repositoryFactory.createAnswerRepository().listAnswersForQuestion(question);
        for (Answer a : answers) {
            int score = repositoryFactory.createAnswerVoteRepository().voteNr(a, true) -
                    repositoryFactory.createAnswerVoteRepository().voteNr(a, false);
            a.setScore(score);
        }
        answers.sort((o1, o2) -> -o1.getScore().compareTo(o2.getScore()));
        return answers;
    }

    @Transactional
    public Answer saveAnswer(Answer answer) {
        return repositoryFactory.createAnswerRepository().save(answer);
    }

    @Transactional
    public Answer saveAnswer(Integer questionId, Integer userId, String answerText) {
        Optional<Question> question = repositoryFactory.createQuestionRepository().findById(questionId);
        if (question.isPresent()) {
            return repositoryFactory.createAnswerRepository().save(new Answer(questionId, userId, answerText));
        } else {
            throw new QuestionNotFoundException();
        }
    }

    @Transactional
    public Answer editAnswer(Integer answerId, Integer userId, String answerText) {
        Optional<Answer> answer = repositoryFactory.createAnswerRepository().findById(answerId);
        if (answer.isPresent()) {
            if (answer.get().getUserId().equals(userId)) {
                answer.get().setText(answerText);
                return repositoryFactory.createAnswerRepository().save(answer.get());
            } else {
                throw new InvalidActionException("You can only edit your answer.");
            }
        } else {
            throw new AnswerNotFoundException();
        }
    }

    @Transactional
    public Answer findById(Integer answerId) {
        Optional<Answer> answer = repositoryFactory.createAnswerRepository().findById(answerId);
        if (answer.isPresent()) {
            int score = repositoryFactory.createAnswerVoteRepository().voteNr(answer.get(), true) -
                    repositoryFactory.createAnswerVoteRepository().voteNr(answer.get(), false);
            answer.get().setScore(score);
            return answer.get();
        } else
            throw new AnswerNotFoundException();
    }

    @Transactional
    public void deleteAnswer(Integer answerId, Integer userId) {
        Optional<Answer> answer = repositoryFactory.createAnswerRepository().findById(answerId);
        if (answer.isPresent()) {
            if (answer.get().getUserId().equals(userId)) {
                repositoryFactory.createAnswerRepository().delete(answer.get());
            } else {
                throw new InvalidActionException("You can only delete your answer.");
            }
        } else {
            throw new AnswerNotFoundException();
        }
    }

}
