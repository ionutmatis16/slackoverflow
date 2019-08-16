package ro.utcn.sd.mid.assign1.slackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.QuestionDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;
import ro.utcn.sd.mid.assign1.slackoverflow.event.QuestionCreatedEvent;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.QuestionNotFoundException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.TagNotFoundException;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.RepositoryFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final RepositoryFactory repositoryFactory;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public QuestionDTO askQuestion(QuestionDTO dto) {
        Question question = new Question(dto.getUserId(),
                dto.getTitle(),
                dto.getText());
        for (String tag : dto.getTags()) {
            Optional<Tag> foundTag = repositoryFactory.createTagRepository().findByTagName(tag);
            if (foundTag.isPresent()) {
                question.addTag(foundTag.get());
            } else {
                question.addTag(repositoryFactory.createTagRepository().save(new Tag(tag)));
            }
        }
        QuestionDTO output = QuestionDTO.ofEntity(repositoryFactory.createQuestionRepository().save(question));
        eventPublisher.publishEvent(new QuestionCreatedEvent(output));
        return output;
    }

    @Transactional
    @SuppressWarnings("Duplicates")
    public QuestionDTO findById(Integer id) {
        Optional<Question> OptQuestion = repositoryFactory.createQuestionRepository().findById(id);
        if (OptQuestion.isPresent()) {
            Question q = OptQuestion.get();
            q.setTags(repositoryFactory.createQuestionRepository().findTagsByQuestion(q));
            int score = repositoryFactory.createQuestionVoteRepository().voteNr(q, true) -
                    repositoryFactory.createQuestionVoteRepository().voteNr(q, false);
            q.setScore(score);
            return QuestionDTO.ofEntity(repositoryFactory.createQuestionRepository().save(q));
        } else
            throw new QuestionNotFoundException();
    }

    @Transactional
    @SuppressWarnings("Duplicates")
    public List<QuestionDTO> listAllQuestions() {
        List<Question> questions = repositoryFactory.createQuestionRepository().findAll();
        Collections.sort(questions);

        for (Question q : questions) {
            q.setTags(repositoryFactory.createQuestionRepository().findTagsByQuestion(q));
            int score = repositoryFactory.createQuestionVoteRepository().voteNr(q, true) -
                    repositoryFactory.createQuestionVoteRepository().voteNr(q, false);
            q.setScore(score);
            repositoryFactory.createQuestionRepository().save(q);
        }

        return questions.stream()
                .map(QuestionDTO::ofEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<QuestionDTO> filterByTag(String tagName) {
        Optional<Tag> tag = repositoryFactory.createTagRepository().findByTagName(tagName);
        if (tag.isPresent()) {
            List<Question> questions = repositoryFactory.createTagRepository().findQuestionsByTag(tag.get());
            for (int i = 0; i < questions.size(); i++) {
                questions.get(i).setTags(repositoryFactory.createQuestionRepository().findTagsByQuestion(questions.get(i)));
                repositoryFactory.createQuestionRepository().save(questions.get(i));
            }
            return questions.stream()
                    .map(QuestionDTO::ofEntity)
                    .collect(Collectors.toList());
        } else {
            throw new TagNotFoundException();
        }
    }

    @Transactional
    public List<QuestionDTO> filterByTitle(String title) {
        List<Question> questions = repositoryFactory.createQuestionRepository().findByTitle(title);
        for (Question q : questions) {
            q.setTags(repositoryFactory.createQuestionRepository().findTagsByQuestion(q));
            repositoryFactory.createQuestionRepository().save(q);
        }
        return questions.stream()
                .map(QuestionDTO::ofEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addTagToQuestion(Tag tag, Question question) {
        repositoryFactory.createTagRepository().addTagToQuestion(tag, question);
    }

    @Transactional
    public void addTagsToQuestion(String tagLine, Question questionToBeCreated) {
        String[] tags = tagLine.split(" ");
        for (String tagString : tags) {
            Optional<Tag> retrievedTag = repositoryFactory.createTagRepository().findByTagName(tagString);
            if (retrievedTag.isPresent()) {
                repositoryFactory.createTagRepository().addTagToQuestion(
                        retrievedTag.get(), questionToBeCreated);
                repositoryFactory.createQuestionRepository().save(questionToBeCreated);
            } else {
                Tag newTag = repositoryFactory.createTagRepository().save(new Tag(tagString));
                repositoryFactory.createTagRepository().addTagToQuestion(
                        newTag, questionToBeCreated);
                repositoryFactory.createQuestionRepository().save(questionToBeCreated);
            }
        }
        repositoryFactory.createQuestionRepository().save(questionToBeCreated);
    }
}
