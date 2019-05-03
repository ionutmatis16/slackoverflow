import sOUserModel from "../model/sOUserModel";
import answerModel from "../model/answerModel";

class AnswersListPresenter {
    onCreate = (questionId) => {
        let newAnswer = answerModel.state.newAnswer;
        answerModel.addAnswer(sOUserModel.state.loggedInSOUser, questionId, newAnswer.text);
        answerModel.changeNewAnswerProperty("text", "");
    };

    onChange = (property, value) => {
        answerModel.changeNewAnswerProperty(property, value);
    };

    onChangeNewAnswer = (answerId, value) => {
        answerModel.changeNewTextForAnswer(answerId, value);
    };

    onEditPress = (answerId) => {
        answerModel.changeEditButtonPressedForAnswer(answerId);
    };

    onEditSubmit = (answerId) => {
        answerModel.editAnswer(answerId);
        answerModel.changeEditButtonPressedForAnswer(answerId);
    };

    onDeleteAnswer = (answerId) => {
        answerModel.deleteAnswer(answerId);
    };
}

const answersListPresenter = new AnswersListPresenter();

export default answersListPresenter;