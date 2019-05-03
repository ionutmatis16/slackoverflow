import voteModel from "../model/voteModel";
import answerModel from "../model/answerModel";
import questionModel from "../model/questionModel";

class VotePresenter {
    onVoteQuestion = (questionId, voteType, sOUsername) => {
        voteModel.addANewQuestionVote(questionId, voteType, sOUsername);
        let newScore = voteModel.calculateScore("questionVotes", questionId);
        questionModel.updateQuestionScore(questionId, newScore);
    };

    onVoteAnswer = (answerId, voteType, sOUsername) => {
        voteModel.addANewAnswerVote(answerId, voteType, sOUsername);
        let newScore = voteModel.calculateScore("answerVotes", answerId);
        answerModel.updateAnswerScore(answerId, newScore);
    };
}

const votePresenter = new VotePresenter();

export default votePresenter;