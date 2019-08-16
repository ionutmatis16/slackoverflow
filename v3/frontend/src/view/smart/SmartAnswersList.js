import React, {Component} from 'react';
import questionModel from "../../model/questionModel";
import AnswersList from "../dumb/AnswersList";
import sOUserModel from "../../model/sOUserModel";
import answerModel from "../../model/answerModel";
import answersListPresenter from "../../presenter/answersListPresenter";
import questionsListPresenter from "../../presenter/questionsListPresenter";
import sOUserPresenter from "../../presenter/sOUserPresenter";
import votePresenter from "../../presenter/votePresenter";


const mapModelStateToComponentState = (questionModel, answerModel, props) => ({
    question: getQuestionById(questionModel.state.questions, props.match.params.id),
    newAnswer: answerModel.state.newAnswer
    //answersToDisplay: answerModel.state.answers//answersListPresenter.onInit(props.match.params.id)
});

function getQuestionById(questions, id) {
    id = parseInt(id);
    for (let i = 0; i < questions.length; i++) {
        if (questions[i].id === id)
            return questions[i];
    }
}

export default class SmartAnswersList extends Component {
    constructor(props) {
        super(props);

        console.log("FILTERED SMART INAINTE ON INIT " + answerModel.state.answers);

        console.log("FILTERED SMART DUPA ON INIT " + answerModel.state.answers);
        this.state = mapModelStateToComponentState(questionModel, answerModel, props);

        this.listener = () =>
            this.setState(mapModelStateToComponentState(questionModel, answerModel, props));
        answerModel.addListener("changeAnswer", this.listener);
        questionModel.addListener("change", this.listener);
        answersListPresenter.onInit(props.match.params.id)
    }


    componentDidUpdate(prevProps) {
        if (prevProps.match.params.index !== this.props.match.params.index) {
            this.setState(mapModelStateToComponentState(questionModel, answerModel, this.props));
        }
    }

    componentWillUnmount() {
        questionModel.removeListener("changeAnswer", this.listener);
    }

    render() {
        return <AnswersList
            question={this.state.question}
            sOUsername={sOUserModel.state.loggedInSOUser}
            answers={answerModel.state.answers}
            newAnswer={this.state.newAnswer}
            onVoteQuestion={votePresenter.onVoteQuestion}
            onVoteAnswer={votePresenter.onVoteAnswer}

            onDeleteAnswer={answersListPresenter.onDeleteAnswer}
            onEditPress={answersListPresenter.onEditPress}
            onEditSubmit={answersListPresenter.onEditSubmit}
            onChangeNewAnswer={answersListPresenter.onChangeNewAnswer}
            onViewAnswers={questionsListPresenter.onViewAnswers}

            onChange={answersListPresenter.onChange}
            onCreate={answersListPresenter.onCreate}
            onLogout={sOUserPresenter.onLogout}
        />
    }
}