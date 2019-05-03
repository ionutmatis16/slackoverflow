import {EventEmitter} from "events"

class VoteModel extends EventEmitter {
    constructor() {
        super();
        this.state = {
            questionVotes: [{
                id: 0,
                questionId: 0,
                votedBy: "b",
                voteType: true //upvote
            }, {
                id: 1,
                questionId: 1,
                votedBy: "u1",
                voteType: false //downvote
            }],
            answerVotes: [{
                id: 0,
                answerId: 0,
                votedBy: "b",
                voteType: false
            }, {
                id: 1,
                answerId: 1,
                votedBy: "u1",
                voteType: true
            }],
            currentQuestionVoteId: 1,
            currentAnswerVoteId: 1
        }
    }

    addANewQuestionVote(questionId, voteType, sOUsername) {
        this.state = {
            ...this.state,
            currentQuestionVoteId: ++this.state.currentQuestionVoteId,
            questionVotes: this.state.questionVotes.concat([{
                id: this.state.currentQuestionVoteId,
                questionId: questionId,
                votedBy: sOUsername,
                voteType: voteType
            }])
        };
    }

    addANewAnswerVote(answerId, voteType, sOUsername) {
        this.state = {
            ...this.state,
            currentAnswerVoteId: ++this.state.currentAnswerVoteId,
            answerVotes: this.state.answerVotes.concat([{
                id: this.state.currentAnswerVoteId,
                answerId: answerId,
                votedBy: sOUsername,
                voteType: voteType
            }])
        };
    }

    calculateScore(questionsOrAnswers, qOrAId) {
        let votes = this.state[questionsOrAnswers];
        let qAVoteId = questionsOrAnswers === "questionVotes" ? "questionId" : "answerId";
        let score = 0;
        for (let i = 0; i < votes.length; i++) {
            if (votes[i][qAVoteId] === qOrAId) {
                score = votes[i].voteType ? (score + 1) : (score - 1);
            }
        }
        return score;
    }
}

const voteModel = new VoteModel();

export default voteModel;