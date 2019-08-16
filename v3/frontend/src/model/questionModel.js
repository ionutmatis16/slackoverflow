import {EventEmitter} from "events";
import sOUserModel, {getClient} from "./sOUserModel";

class QuestionModel extends EventEmitter {  // observable in js
    // can attach listeners, to detach them, to emit events
    constructor() {
        super();
        // define some state
        this.state = {
            questions: [],
            currentId: 1,
            newQuestion: {
                id: -1,
                userName: "", // not shown on form
                title: "",
                text: "",
                creationDate: new Date().toLocaleString(),
                tags: [],
                score: 0
            },
            filterTitle: true, // false will filter tags
            filterText: "",
            filteredQuestions: []
        };
    }

    loadQuestions() {
        return getClient().loadAllQuestions()
            .then((questions) => {
                if (questions !== undefined) {
                    for (let i = 0; i < questions.length; i++) {
                        questions[i].userName = sOUserModel.getSOUsername(parseInt(questions[i].userId));
                    }
                    this.state = {
                        ...this.state,
                        questions: questions
                    };
                    this.emit("change", this.state);
                }

            })
    }

    addQuestion = (userName, title, text, tags) => {
        let userId = sOUserModel.getSOUserId(userName);
        return getClient().createQuestion(userId, title, text, tags)
            .then(questionCreated => {
                if (!this.questionAlreadyExists(questionCreated.id)) {
                    this.appendQuestion(questionCreated);
                    this.emit("change", this.state);

                }
            });
    };

    voteQuestion = (questionId, userName, voteType) => {
        let userId = sOUserModel.getSOUserId(userName);
        return getClient().voteQuestion(questionId, userId, voteType)
            .then(updatedQuestion => this.updateQuestionsState(updatedQuestion))
    };

    updateQuestionsState(questionDTOFomBackend) {
        let questions = this.state.questions;
        for (let i = 0; i < questions.length; i++) {
            if (questions[i].id === questionDTOFomBackend.id) {
                questions[i] = questionDTOFomBackend;
                questions[i].userName = sOUserModel.getSOUsername(parseInt(questions[i].userId));
            }
        }
        this.emit("change", this.state);

    }

    clearQuestions() {
        this.state = {
            ...this.state,
            questions: []
        };
        this.emit("change", this.state);
    }

    questionAlreadyExists(questionId) {
        let questions = this.state.questions;
        for (let i = 0; i < questions.length; i++) {
            if (questions[i].id === questionId)
                return true;
        }
        return false;
    }

    appendQuestion(questionCreated) {
        questionCreated.userName = sOUserModel.getSOUsername(questionCreated.userId);
        this.state = {
            ...this.state,
            questions: [questionCreated].concat(this.state.questions)//.concat([questionCreated])
        };
        this.emit("change", this.state);
    }

    changeNewQuestionProperty(property, value) {
        this.state = {
            ...this.state, // contains all the properties of the old state, copied
            newQuestion: {
                ...this.state.newQuestion,
                [property]: value // the property contained will be changed
            }
        };
        this.emit("change", this.state); // the state has changed, passed the new state as arg
    }

    changeMainStateProperty(property, value) {
        this.state = {
            ...this.state,
            [property]: value
        };
        this.emit("change", this.state);
    }

    filterQuestions(filterProperty, filterText) {
        return getClient().filterQuestions(filterProperty, filterText)
            .then(filteredQuestions => {
                for (let i = 0; i < filteredQuestions.length; i++) {
                    filteredQuestions[i].userName = sOUserModel.getSOUsername(parseInt(filteredQuestions[i].userId));
                }
                this.changeMainStateProperty("filteredQuestions", filteredQuestions)
            })
    }

    getAllTags() {
        let allQuestions = this.state.questions;
        let allTags = new Set();
        for (let i = 0; i < allQuestions.length; i++) {
            allQuestions[i].tags.forEach(tag => allTags.add(tag));
        }
        return Array.from(allTags).sort();
    }
}

const questionModel = new QuestionModel();


export default questionModel; // make it singleton