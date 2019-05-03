import questionModel from "../model/questionModel";
import sOUserModel from "../model/sOUserModel";

class QuestionsListPresenter {
    onCreate = () => {
        let newQuestion = questionModel.state.newQuestion;
        let newTags;
        newTags = newQuestion.tags.length > 0 ? newQuestion.tags.trim().split(" ") : [];
        questionModel.addQuestion(sOUserModel.state.loggedInSOUser, newQuestion.title,
            newQuestion.text, Array.from(new Set(newTags)));
        questionModel.changeNewQuestionProperty("title", "");
        questionModel.changeNewQuestionProperty("text", "");
        questionModel.changeNewQuestionProperty("tags", "");
    };

    onChange = (property, value) => {
        questionModel.changeNewQuestionProperty(property, value);
    };

    onSearchChange = (property, value) => {
        questionModel.changeMainStateProperty(property, value);
    };

    onChangeFilter = () => {
        let currentFilter = questionModel.state.filterTitle;
        questionModel.changeMainStateProperty("filterTitle", !currentFilter);
    };

    onSearchClick = () => {
        let filterText = questionModel.state.filterText;
        let filterName = questionModel.state.filterTitle ? "title" : "tags";

        questionModel.filterQuestions(filterName, filterText);
        window.location.assign("#/questions/filter");
    };

    onAvailableTagsClick = (tagName) => {
        let currentTags = questionModel.state.newQuestion.tags;
        questionModel.changeNewQuestionProperty("tags", currentTags.toString().concat(tagName + " "));
    };


    onViewAnswers = (index) => {
        window.location.assign("#/question/" + index);
    }
}

const questionsListPresenter = new QuestionsListPresenter();

export default questionsListPresenter;