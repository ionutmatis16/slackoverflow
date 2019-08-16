package ro.utcn.sd.mid.assign1.slackoverflow.commandDP.answerCommand;

import lombok.AllArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Command;
import ro.utcn.sd.mid.assign1.slackoverflow.service.AnswerService;

@AllArgsConstructor
public class EditAnswerCommand implements Command {
    private AnswerService answerService;
    private Integer answerId;
    private Integer questionId;
    private String name;
    private String newText;

    @Override
    public Object execute() {
        return answerService.editAnswer(answerId, questionId, name, newText);
    }
}
