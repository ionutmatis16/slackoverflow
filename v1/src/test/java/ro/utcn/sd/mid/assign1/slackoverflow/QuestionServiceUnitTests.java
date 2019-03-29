package ro.utcn.sd.mid.assign1.slackoverflow;

import org.junit.Assert;
import org.junit.Test;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.RepositoryFactory;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.memory.InMemoryRepositoryFactory;
import ro.utcn.sd.mid.assign1.slackoverflow.service.QuestionService;

import java.sql.Timestamp;
import java.util.ArrayList;

public class QuestionServiceUnitTests {
    private static Question q1 = new Question(1,1, "Title1", "Question 1",new Timestamp(System.currentTimeMillis()), new ArrayList<>(),0);
    private static Question q2 = new Question(2,1, "Title2", "Question 2",new Timestamp(System.currentTimeMillis()), new ArrayList<>(),0);

    private static Tag t1 = new Tag(1,"tag1", new ArrayList<>());
    private static Tag t2 = new Tag(1,"tag2", new ArrayList<>());

    private static RepositoryFactory createMockedFactory() {
        RepositoryFactory factory = new InMemoryRepositoryFactory();
        factory.createTagRepository().addTagToQuestion(t1,q1);
        factory.createTagRepository().addTagToQuestion(t2,q2);
        factory.createQuestionRepository().save(q1);
        factory.createQuestionRepository().save(q2);
        return factory;
    }

    @Test
    public void testTitleFilterAndTagAdding() {
        RepositoryFactory factory = createMockedFactory();
        QuestionService questionService = new QuestionService(factory);

        // Check the titles
        Assert.assertEquals(1,questionService.filterByTitle("1").size());
        Assert.assertEquals(2,questionService.filterByTitle("title").size());

        // add one more tag besides tag1
        questionService.addTagToQuestion(new Tag(1,"tag3", new ArrayList<>()),q1);
        Assert.assertEquals(2, questionService.findById(q1.getId()).getTags().size());

    }
}
