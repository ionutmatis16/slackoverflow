package ro.utcn.sd.mid.assign1.slackoverflow.commandDP.questionCommand;

import lombok.AllArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Command;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.QuestionDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.service.QuestionService;

@AllArgsConstructor
public class CreateQuestionCommand implements Command {
    private QuestionService questionService;
    private QuestionDTO dto;

    @Override
    public Object execute() {
        return questionService.askQuestion(dto);
    }
}
