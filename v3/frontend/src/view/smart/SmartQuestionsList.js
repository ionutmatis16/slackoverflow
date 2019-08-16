import React, {Component} from 'react';
import questionModel from '../../model/questionModel';
import QuestionsList from '../dumb/QuestionsList';
import questionsListPresenter from '../../presenter/questionsListPresenter';
import sOUserModel from "../../model/sOUserModel";
import sOUserPresenter from "../../presenter/sOUserPresenter";
import votePresenter from "../../presenter/votePresenter";

const mapModelStateToComponentState = (questionModel) => ({
    questions: questionModel.questions,
    newQuestion: questionModel.newQuestion,
    filterTitle: questionModel.filterTitle,
    filterText: questionModel.filterText,
    allTags: allTags(),
});

function allTags() {
    return questionModel.getAllTags();
}

// does not have any input, pulls everything from the model
export default class SmartQuestionsList extends Component {
    constructor(props) {
        super(props);
        // compute initial state, will have an internal state which will derive from model
        // create & attach a listener to the model  

        this.state = mapModelStateToComponentState(questionModel.state); //initial state
        // called when the state is updated, get as a param the new state 
        // when the state is updated, I am notified, I recompute my component's state & update it 
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        questionModel.addListener("change", this.listener);
        questionsListPresenter.onInit();

    }


    // prevent memory leaks
    componentWillUnmount() {
        questionModel.removeListener("change", this.listener);
    }


    render() {
        return <QuestionsList
            questions={this.state.questions}
            allTags={this.state.allTags}
            newQuestion={this.state.newQuestion}
            onVote={votePresenter.onVoteQuestion}

            onCreate={questionsListPresenter.onCreate}
            onChange={questionsListPresenter.onChange}
            sOUsername={sOUserModel.state.loggedInSOUser}
            filterText={this.state.filterText}

            onChangeFilter={questionsListPresenter.onChangeFilter}
            filterTitle={this.state.filterTitle}
            onSearchClick={questionsListPresenter.onSearchClick}
            onSearchChange={questionsListPresenter.onSearchChange}
            onLogout={sOUserPresenter.onLogout}

            onViewAnswers={questionsListPresenter.onViewAnswers}
            onAvailableTagsClick={questionsListPresenter.onAvailableTagsClick}
        />
    }
}