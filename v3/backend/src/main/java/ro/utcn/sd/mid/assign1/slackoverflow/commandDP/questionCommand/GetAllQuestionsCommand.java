package ro.utcn.sd.mid.assign1.slackoverflow.commandDP.questionCommand;

import lombok.AllArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Command;
import ro.utcn.sd.mid.assign1.slackoverflow.service.QuestionService;

@AllArgsConstructor
public class GetAllQuestionsCommand implements Command {
    private QuestionService questionService;

    @Override
    public Object execute() {
        return questionService.listAllQuestions();
    }
}
