package ro.utcn.sd.mid.assign1.slackoverflow.repository.memory;

import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.AnswerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryAnswerRepository extends InMemoryAbstractRepository<Answer>
        implements AnswerRepository {
    @Override
    public List<Answer> listAnswersForQuestion(Question question) {
        List<Answer> answers = findAll();
        List<Answer> result = new ArrayList<>();
        for (Answer a : answers) {
            if (a.getQuestionId().equals(question.getId())) {
                result.add(a);
            }
        }
        return result;
    }

    @Override
    public List<Answer> findSOUserAnswers(SOUser soUser) {
        return getData().values().stream().
                filter(answer -> answer.getUserId().equals(soUser.getId())).
                collect(Collectors.toList());
    }
}
