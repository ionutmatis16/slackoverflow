package ro.utcn.sd.mid.assign1.slackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.*;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.InvalidNameOrPasswordException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.NameAlreadyExistsException;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.RepositoryFactory;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.SOUserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SOUserService {
    private final RepositoryFactory repositoryFactory;

    @Transactional
    public SOUser registerSOUser(String sOUsername, String sOPassword) {
        SOUserRepository sr = repositoryFactory.createSOUserRepository();
        Optional<SOUser> sOUser = sr.findBySOUsername(sOUsername);
        if (sOUser.isPresent()) {
            throw new NameAlreadyExistsException();
        } else {
            return sr.save(new SOUser(sOUsername, sOPassword));
        }
    }

    @Transactional
    public List<SOUser> findAllSOUsers() {
        return repositoryFactory.createSOUserRepository().findAll();
    }

    @Transactional
    public SOUser loginSOUser(String sOUsername, String sOPassword) {
        SOUserRepository sr = repositoryFactory.createSOUserRepository();
        Optional<SOUser> sOUser = sr.findBySOUsername(sOUsername);
        if (sOUser.isPresent() && sOUser.get().getSOPassword().equals(sOPassword))
            return sOUser.get();
        else
            throw new InvalidNameOrPasswordException();
    }

    @Transactional
    public SOUser calculateSOUserScore(SOUser sOUser) {
        int score = 0;
        List<QuestionVote>  questionVotes = repositoryFactory.createQuestionVoteRepository().findAll();
        List<AnswerVote>  answerVotes = repositoryFactory.createAnswerVoteRepository().findAll();
        List<Question> questionsOfSOUser = repositoryFactory.createQuestionRepository().findSOUserQuestions(sOUser);
        List<Answer>  answersOfSOUser = repositoryFactory.createAnswerRepository().findSOUserAnswers(sOUser);

        for(Question question : questionsOfSOUser) {
            for (QuestionVote questionVote : questionVotes) {
                if (questionVote.getQuestionId().equals(question.getId())) {
                    if (questionVote.getVoteType()) {
                        score+=5;
                    } else {
                        score-=2;
                    }
                }
            }
        }

        for (Answer answer : answersOfSOUser) {
            for (AnswerVote answerVote : answerVotes) {
                if (answerVote.getAnswerId().equals(answer.getId())) {
                    if (answerVote.getVoteType()) {
                        score+=10;
                    } else {
                        score-=2;
                    }
                }
            }
        }

        for (AnswerVote answerVote : answerVotes) {
            if (answerVote.getUserId().equals(sOUser.getId()) && !answerVote.getVoteType()) {
                score-=1;
            }
        }

        sOUser.setScore(score);
        return repositoryFactory.createSOUserRepository().save(sOUser);
    }
}
