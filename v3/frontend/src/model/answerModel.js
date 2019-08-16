import {EventEmitter} from "events";
import sOUserModel, {getClient} from "./sOUserModel";

class AnswerModel extends EventEmitter {
    constructor() {
        super();
        this.state = {
            answers: [/*{
                id: 0,
                userName: "u2",
                questionId: 1,
                text: "text of first answer",
                creationDate: new Date().toLocaleString(),
                score: -1,
                editButtonPressed: false,
                newAnswerText: "text of first answer"
            },
                {
                    id: 1,
                    userName: "b",
                    questionId: 0,
                    text: "text of second answer",
                    creationDate: new Date().toLocaleString(),
                    score: 1,
                    editButtonPressed: false,
                    newAnswerText: "text of second answer"
                }*/
            ],
            currentId: 1,
            newAnswer: {
                userName: "", // not shown on form
                questionId: -1,
                text: "",
                creationDate: new Date().toLocaleString(),
                score: 0,
                editButtonPressed: false
            },
        }
    }

    addAnswer(userName, questionId, text) {
        return getClient().createAnswer(questionId, sOUserModel.getSOUserId(userName), text)
            .then(answer => {
                if (!this.answerAlreadyExists(answer.id)) {
                    this.appendAnswer(answer);
                    //this.emit("changeAnswer", this.state); // the state has changed, passed the new state as arg
                }
            })
    }

    appendAnswer(answer) {
        answer.userName = sOUserModel.getSOUsername(answer.userId);
        answer.editButtonPressed = false;
        answer.newAnswerText = answer.text;
        this.state = {
            ...this.state,
            answers: [answer].concat(this.state.answers)//.concat([questionCreated])
        };
        this.emit("changeAnswer", this.state);
    }

    findAnswersByQuestionId = (questionId) => {
        return getClient().findAnswersByQuestionId(questionId)
            .then(answers => {
                if (answers !== undefined) {
                    for (let i = 0; i < answers.length; i++) {
                        answers[i].userName = sOUserModel.getSOUsername(parseInt(answers[i].userId));
                        answers[i].editButtonPressed = false;
                        answers[i].newAnswerText = answers[i].text;
                    }
                    this.state = {
                        ...this.state,
                        answers: answers
                    };
                    this.emit("changeAnswer", this.state);
                }
            })
        //this.state.answers.filter(answer => answer.questionId === intId);
    };

    voteAnswer (answerId, userName, voteType) {
        let userId = sOUserModel.getSOUserId(userName);
        return getClient().voteAnswer(answerId, userId, voteType)
            .then(answerDTOFromBackend => this.updateAnswersState(answerDTOFromBackend));
    }

    updateAnswersState(answerDTOFromBackend) {
        let answers = this.state.answers;
        for (let i = 0; i < answers.length; i++) {
            if (answers[i].id === answerDTOFromBackend.id) {
                answers[i] = answerDTOFromBackend;
                answers[i].userName = sOUserModel.getSOUsername(parseInt(answers[i].userId));
            }
        }
        this.emit("changeAnswer", this.state);
    }

    answerAlreadyExists(answerId) {
        let answers = this.state.answers;
        for (let i = 0; i < answers.length; i++) {
            if (answers[i].id === answerId)
                return true;
        }
        return false;
    }

    changeNewTextForAnswer(answerId, value) {
        let answers = this.state.answers;

        for (let i = 0; i < answers.length; i++) {
            if (answers[i].id === answerId) {
                answers[i].newAnswerText = value;
            }
        }
        this.state = {
            ...this.state,
            answers: answers
        };
        this.emit("changeAnswer", this.state);
    }

    changeMainStateProperty(property, value) {
        this.state = {
            ...this.state,
            [property]: value
        };
        this.emit("changeAnswer", this.state);
    }

    changeEditButtonPressedForAnswer(answerId) {
        let answers = this.state.answers;
        for (let i = 0; i < answers.length; i++) {
            if (answers[i].id === answerId) {
                answers[i].editButtonPressed = !answers[i].editButtonPressed;
            }
        }
        this.state = {
            ...this.state,
            answers: answers
        };
        this.emit("changeAnswer", this.state);
    }

    editAnswer(answerId, questionId) {
        let newAnswerText = this.findNewAnswerTextForAnswer(answerId);
        return getClient().editAnswer(answerId, questionId, newAnswerText)
            .then(editedAnswer => this.modifyTextForAnswer(editedAnswer))
    }

    modifyTextForAnswer(editedAnswer) {
        let answers = this.state.answers;
        for (let i = 0; i < answers.length; i++) {
            if (editedAnswer.id === answers[i].id) {
                answers[i].text = editedAnswer.text;
            }
        }
        this.state = {
            ...this.state,
            answers: answers
        };
        this.emit("changeAnswer", this.state);
    }

    deleteAnswer(answerId, questionId) {
        getClient().deleteAnswer(answerId, questionId)
            .then(deletedAnswerId => {
                console.log(deletedAnswerId);
                this.updateAnswersAfterDelete(deletedAnswerId)
            });

    }

    updateAnswersAfterDelete(answerId) {
        let answers = this.state.answers;
        for (let i = 0; i < answers.length; i++) {
            if (answerId === answers[i].id) {
                answers.splice(i, 1);
            }
        }
        this.state = {
            ...this.state,
            answers: answers
        };
        this.emit("changeAnswer", this.state);
    }

    changeNewAnswerProperty(property, value) {
        this.state = {
            ...this.state, // contains all the properties of the old state, copied
            newAnswer: {
                ...this.state.newAnswer,
                [property]: value // the property contained will be changed
            }
        };
        this.emit("changeAnswer", this.state); // the state has changed, passed the new state as arg
    }

    findNewAnswerTextForAnswer(answerId) {
        let answers = this.state.answers;
        for (let i = 0; i < answers.length; i++) {
            if (answerId === answers[i].id) {
                return answers[i].newAnswerText;
            }
        }
        return "";
    }
}

const answerModel = new AnswerModel();

export default answerModel;
