package ro.utcn.sd.mid.assign1.slackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.QuestionNotFoundException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.TagNotFoundException;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.RepositoryFactory;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final RepositoryFactory repositoryFactory;

    @Transactional
    public Question askQuestion(Question question) {
        return repositoryFactory.createQuestionRepository().save(question);
    }

    @Transactional
    @SuppressWarnings("Duplicates")
    public Question findById(Integer id) {
        Optional<Question> OptQuestion = repositoryFactory.createQuestionRepository().findById(id);
        if (OptQuestion.isPresent()) {
            Question q = OptQuestion.get();
            q.setTags(repositoryFactory.createQuestionRepository().findTagsByQuestion(q));
            int score = repositoryFactory.createQuestionVoteRepository().voteNr(q, true) -
                    repositoryFactory.createQuestionVoteRepository().voteNr(q,false);
            q.setScore(score);
            return repositoryFactory.createQuestionRepository().save(q);
        } else
            throw new QuestionNotFoundException();
    }

    @Transactional
    @SuppressWarnings("Duplicates")
    public List<Question> listAllQuestions() {
        List<Question> questions = repositoryFactory.createQuestionRepository().findAll();
        Collections.sort(questions);

        for (Question q : questions) {
            q.setTags(repositoryFactory.createQuestionRepository().findTagsByQuestion(q));
            int score = repositoryFactory.createQuestionVoteRepository().voteNr(q, true) -
                    repositoryFactory.createQuestionVoteRepository().voteNr(q, false);
            q.setScore(score);
            repositoryFactory.createQuestionRepository().save(q);
        }

        return questions;
    }

    @Transactional
    public List<Question> filterByTag(String tagName) {
        Optional<Tag> tag = repositoryFactory.createTagRepository().findByTagName(tagName);
        if (tag.isPresent()) {
            List<Question> questions = repositoryFactory.createTagRepository().findQuestionsByTag(tag.get());
            for (int i = 0; i < questions.size(); i++) {
                questions.get(i).setTags(repositoryFactory.createQuestionRepository().findTagsByQuestion(questions.get(i)));
                repositoryFactory.createQuestionRepository().save(questions.get(i));
            }
            return questions;
        } else {
            throw new TagNotFoundException();
        }
    }

    @Transactional
    public List<Question> filterByTitle(String title) {
        List<Question> questions = repositoryFactory.createQuestionRepository().findByTitle(title);
        for (Question q : questions) {
            q.setTags(repositoryFactory.createQuestionRepository().findTagsByQuestion(q));
            repositoryFactory.createQuestionRepository().save(q);
        }
        return questions;
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
