package ro.utcn.sd.mid.assign1.slackoverflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Invoker;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.questionCommand.CreateQuestionCommand;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.questionCommand.FilterByTagCommand;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.questionCommand.FilterByTitleCommand;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.questionCommand.GetAllQuestionsCommand;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.QuestionDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.service.QuestionService;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final Invoker invoker;

    @GetMapping("/questions")
    public Object readAll(@RequestParam(required = false) String filterTitle,
                          @RequestParam(required = false) String filterTag) {
        if (filterTitle == null && filterTag == null) {
            invoker.setCommand(new GetAllQuestionsCommand(questionService));
        } else {
            if (filterTitle != null) {
                invoker.setCommand(new FilterByTitleCommand(questionService, filterTitle));
            } else {
                invoker.setCommand(new FilterByTagCommand(questionService, filterTag));
            }
        }
        return invoker.invoke();
    }

    @PostMapping("/questions")
    public Object create(@RequestBody QuestionDTO dto) {
        invoker.setCommand(new CreateQuestionCommand(questionService, dto));
        return invoker.invoke();
    }
}
