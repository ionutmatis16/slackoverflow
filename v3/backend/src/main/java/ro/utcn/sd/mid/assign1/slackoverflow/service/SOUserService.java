package ro.utcn.sd.mid.assign1.slackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.LoginRegisterDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.SOUserDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.*;
import ro.utcn.sd.mid.assign1.slackoverflow.event.SOUserCreatedEvent;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.InvalidNameOrPasswordException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.NameAlreadyExistsException;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.RepositoryFactory;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.SOUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SOUserService {
    private final RepositoryFactory repositoryFactory;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public SOUserDTO registerSOUser(LoginRegisterDTO dto) {
        SOUser soUser = new SOUser();
        soUser.setSOUsername(dto.getSoUsername());
        soUser.setSOPassword(passwordEncoder.encode(dto.getSoPassword()));
        soUser.setScore(0);

        SOUserRepository sr = repositoryFactory.createSOUserRepository();
        Optional<SOUser> sOUser = sr.findBySOUsername(soUser.getSOUsername());
        if (sOUser.isPresent()) {
            throw new NameAlreadyExistsException();
        } else {
            SOUserDTO output = SOUserDTO.ofEntity(sr.save(soUser));
            eventPublisher.publishEvent(new SOUserCreatedEvent(output));
            return output;
        }
    }

    @Transactional
    public List<SOUserDTO> findAllSOUsers() {
        return repositoryFactory.createSOUserRepository().findAll()
                .stream()
                .map(this::calculateSOUserScore)
                .map(SOUserDTO::ofEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public SOUserDTO loginSOUser(LoginRegisterDTO dto) {
        SOUserRepository sr = repositoryFactory.createSOUserRepository();
        Optional<SOUser> sOUser = sr.findBySOUsername(dto.getSoUsername());
        if (sOUser.isPresent() && passwordEncoder.matches(dto.getSoPassword(),
                sOUser.get().getSOPassword()))
            return SOUserDTO.ofEntity(sOUser.get());
        else
            throw new InvalidNameOrPasswordException();
    }

    @Transactional
    public SOUser calculateSOUserScore(SOUser sOUser) {
        int score = 0;
        List<QuestionVote> questionVotes = repositoryFactory.createQuestionVoteRepository().findAll();
        List<AnswerVote> answerVotes = repositoryFactory.createAnswerVoteRepository().findAll();
        List<Question> questionsOfSOUser = repositoryFactory.createQuestionRepository().findSOUserQuestions(sOUser);
        List<Answer> answersOfSOUser = repositoryFactory.createAnswerRepository().findSOUserAnswers(sOUser);

        for (Question question : questionsOfSOUser) {
            for (QuestionVote questionVote : questionVotes) {
                if (questionVote.getQuestionId().equals(question.getId())) {
                    if (questionVote.getVoteType()) {
                        score += 5;
                    } else {
                        score -= 2;
                    }
                }
            }
        }

        for (Answer answer : answersOfSOUser) {
            for (AnswerVote answerVote : answerVotes) {
                if (answerVote.getAnswerId().equals(answer.getId())) {
                    if (answerVote.getVoteType()) {
                        score += 10;
                    } else {
                        score -= 2;
                    }
                }
            }
        }

        for (AnswerVote answerVote : answerVotes) {
            if (answerVote.getUserId().equals(sOUser.getId()) && !answerVote.getVoteType()) {
                score -= 1;
            }
        }

        sOUser.setScore(score);
        return repositoryFactory.createSOUserRepository().save(sOUser);
    }
}
