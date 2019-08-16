package ro.utcn.sd.mid.assign1.slackoverflow.commandDP.answerCommand;

import lombok.AllArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Command;
import ro.utcn.sd.mid.assign1.slackoverflow.service.AnswerService;

@AllArgsConstructor
public class DeleteAnswerCommand implements Command {
    private AnswerService answerService;
    private Integer answerId;
    private Integer questionId;
    private String name;

    @Override
    public Object execute() {
        return answerService.deleteAnswer(answerId, questionId, name);
    }
}
