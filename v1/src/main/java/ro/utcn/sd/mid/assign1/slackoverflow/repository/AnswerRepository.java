package ro.utcn.sd.mid.assign1.slackoverflow.repository;

import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;

import java.util.List;

public interface AnswerRepository extends AbstractRepository<Answer> {
    List<Answer> listAnswersForQuestion(Question question);
    List<Answer> findSOUserAnswers(SOUser soUser);
}
