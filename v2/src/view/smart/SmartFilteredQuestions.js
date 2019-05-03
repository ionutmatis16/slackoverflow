import React, {Component} from 'react';
import questionModel from '../../model/questionModel';
import sOUserModel from "../../model/sOUserModel";
import FilteredQuestions from "../dumb/FilteredQuestions";
import questionsListPresenter from "../../presenter/questionsListPresenter";
import votePresenter from "../../presenter/votePresenter";

const mapModelStateToComponentState = (questionModel) => ({
    filteredQuestions: questionModel.filteredQuestions,
    filterTitle: questionModel.filterTitle,
    filterText: questionModel.filterText
});

// does not have any input, pulls everything from the model
export default class SmartFilteredQuestions extends Component {
    constructor(props) {
        super(props);
        // compute initial state, will have an internal state which will derive from model
        // create & attach a listener to the model

        this.state = mapModelStateToComponentState(questionModel.state); //initial state
        // called when the state is updated, get as a param the new state
        // when the state is updated, I am notified, I recompute my component's state & update it
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        questionModel.addListener("change", this.listener);
    }


    // prevent memory leaks
    componentWillUnmount() {
        questionModel.removeListener("change", this.listener);
    }


    render() {
        return <FilteredQuestions
            filteredQuestions={questionModel.state.filteredQuestions}
            sOUsername={sOUserModel.state.loggedInSOUser}
            filterTitle={this.state.filterTitle}
            filterText={this.state.filterText}

            onViewAnswers={questionsListPresenter.onViewAnswers}
            onVote={votePresenter.onVoteQuestion}
        />
    }
}