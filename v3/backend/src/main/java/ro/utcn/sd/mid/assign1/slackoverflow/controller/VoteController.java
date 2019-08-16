package ro.utcn.sd.mid.assign1.slackoverflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Invoker;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.answerCommand.VoteAnswerCommand;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.questionCommand.VoteQuestionCommand;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.AnswerVoteDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.QuestionVoteDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.service.AnswerService;
import ro.utcn.sd.mid.assign1.slackoverflow.service.QuestionService;
import ro.utcn.sd.mid.assign1.slackoverflow.service.VoteService;

@RestController
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final Invoker invoker;

    @PostMapping("/questions/{questionId}/votes")
    public Object voteQuestion(
            @PathVariable Integer questionId, @RequestBody QuestionVoteDTO dto) {
        invoker.setCommand(new VoteQuestionCommand(voteService, questionService, questionId, dto));
        return invoker.invoke();
    }

    @PostMapping("/questions/{questionId}/answers/{answerId}/votes")
    public Object voteAnswer(
            @PathVariable Integer questionId,
            @PathVariable Integer answerId,
            @RequestBody AnswerVoteDTO dto) {
        invoker.setCommand(new VoteAnswerCommand(voteService, answerService, questionId, answerId, dto));
        return invoker.invoke();
    }
}
