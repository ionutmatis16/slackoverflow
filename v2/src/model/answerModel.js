import {EventEmitter} from "events";

class AnswerModel extends EventEmitter {
    constructor() {
        super();
        this.state = {
            answers: [{
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
                }
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
        this.state = {
            ...this.state, // contains all the properties of the old state, copied
            currentId: ++this.state.currentId,
            answers: this.state.answers.concat([{
                id: this.state.currentId,
                userName: userName,
                questionId: questionId,
                text: text,
                creationDate: new Date().toLocaleString(),
                score: 0,
                editButtonPressed: false,
                newAnswerText: text
            }])
        };
        this.emit("changeAnswer", this.state); // the state has changed, passed the new state as arg
    }

    findAnswersByQuestionId(questionId) {
        let intId = parseInt(questionId);
        return this.state.answers.filter(answer => answer.questionId === intId);
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

    editAnswer(answerId) {
        let answers = this.state.answers;
        for (let i = 0; i < answers.length; i++) {
            if (answerId === answers[i].id) {
                answers[i].text = answers[i].newAnswerText;
            }
        }
        this.state = {
            ...this.state,
            answers: answers
        };
        this.emit("changeAnswer", this.state);
    }

    deleteAnswer(answerId) {
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

    updateAnswerScore(answerId, newScore) {
        let answers = this.state.answers;
        for (let i = 0; i < answers.length; i++) {
            if (answers[i].id === answerId) {
                answers[i].score = newScore;
            }
        }
        this.state = {
            ...this.state,
            answers: answers
        };
        this.emit("changeAnswer", this.state);
    }
}

const answerModel = new AnswerModel();

export default answerModel;
