package ro.utcn.sd.mid.assign1.slackoverflow.commandDP.questionCommand;

import lombok.AllArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Command;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.QuestionVoteDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.service.QuestionService;
import ro.utcn.sd.mid.assign1.slackoverflow.service.VoteService;

@AllArgsConstructor
public class VoteQuestionCommand implements Command {
    private VoteService voteService;
    private QuestionService questionService;
    private Integer questionId;
    private QuestionVoteDTO dto;

    @Override
    public Object execute() {
        voteService.voteQuestion(questionId, dto);
        return questionService.findById(questionId);
    }
}
