package ro.utcn.sd.mid.assign1.slackoverflow.commandDP.answerCommand;

import lombok.AllArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Command;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.AnswerVoteDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.service.AnswerService;
import ro.utcn.sd.mid.assign1.slackoverflow.service.VoteService;

@AllArgsConstructor
public class VoteAnswerCommand implements Command {
    private VoteService voteService;
    private AnswerService answerService;
    private Integer questionId;
    private Integer answerId;
    private AnswerVoteDTO dto;


    @Override
    public Object execute() {
        voteService.voteAnswer(questionId, answerId, dto);
        return answerService.findById(answerId);
    }
}
