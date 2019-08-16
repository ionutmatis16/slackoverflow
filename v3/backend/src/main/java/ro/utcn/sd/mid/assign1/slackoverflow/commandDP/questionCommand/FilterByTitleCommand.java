package ro.utcn.sd.mid.assign1.slackoverflow.commandDP.questionCommand;

import lombok.AllArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Command;
import ro.utcn.sd.mid.assign1.slackoverflow.service.QuestionService;

@AllArgsConstructor
public class FilterByTitleCommand implements Command {
    private QuestionService questionService;
    private String title;

    @Override
    public Object execute() {
        return questionService.filterByTitle(title);
    }
}
