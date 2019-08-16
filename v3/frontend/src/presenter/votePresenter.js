import voteModel from "../model/voteModel";
import answerModel from "../model/answerModel";
import questionModel from "../model/questionModel";

class VotePresenter {
    onVoteQuestion = (questionId, voteType, sOUsername) => {
        voteModel.addANewQuestionVote(questionId, voteType, sOUsername);
        questionModel.voteQuestion(questionId, sOUsername, voteType);
    };

    onVoteAnswer = (answerId, voteType, sOUsername) => {
        voteModel.addANewAnswerVote(answerId, voteType, sOUsername);
        answerModel.voteAnswer(answerId, sOUsername, voteType);
    };
}

const votePresenter = new VotePresenter();

export default votePresenter;