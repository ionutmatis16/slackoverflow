package ro.utcn.sd.mid.assign1.slackoverflow.seed;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.RepositoryFactory;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Order(-10)
public class SOSeed implements CommandLineRunner {
    private final RepositoryFactory rf;

    @Override
    @Transactional
    public void run(String... args) {
        if (rf.createSOUserRepository().findAll().isEmpty()) {
            rf.createSOUserRepository().save(new SOUser("user1", "p1"));
            rf.createSOUserRepository().save(new SOUser("user2", "p2"));
        }

        if (rf.createTagRepository().findAll().isEmpty()) {
            rf.createTagRepository().save(new Tag("tag1"));
            rf.createTagRepository().save(new Tag("tag2"));
        }

        if (rf.createQuestionRepository().findAll().isEmpty()) {
            Optional<Tag> tag1 = rf.createTagRepository().findByTagName("tag1");
            Optional<Tag> tag2 = rf.createTagRepository().findByTagName("tag2");
            Question q12 = new Question(1, "First Q", "Is this the first question?");
            Question q22 = new Question(2, "Second Q", "Is this the second question?");

            rf.createTagRepository().addTagToQuestion(tag1.get(), q12);
            rf.createTagRepository().addTagToQuestion(tag2.get(), q12);
            rf.createTagRepository().addTagToQuestion(tag2.get(), q22);

            rf.createQuestionRepository().save(q12);
            rf.createQuestionRepository().save(q22);
        }

        if (rf.createAnswerRepository().findAll().isEmpty()) {
            rf.createAnswerRepository().save(new Answer(1, 2, "Ans1"));
            rf.createAnswerRepository().save(new Answer(2, 1, "Ans2"));
        }

    }
}
