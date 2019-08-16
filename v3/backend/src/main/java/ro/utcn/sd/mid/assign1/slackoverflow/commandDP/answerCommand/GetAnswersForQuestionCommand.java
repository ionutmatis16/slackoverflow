package ro.utcn.sd.mid.assign1.slackoverflow.commandDP.answerCommand;

import lombok.AllArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Command;
import ro.utcn.sd.mid.assign1.slackoverflow.service.AnswerService;

@AllArgsConstructor
public class GetAnswersForQuestionCommand implements Command {
    private AnswerService answerService;
    private Integer questionId;

    @Override
    public Object execute() {
        return answerService.listAnswersForQuestion(questionId);
    }
}
