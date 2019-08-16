import React from 'react';
import MyNavbar from "./MyNavbar";
import MyVoteSystem from "./MyVoteSystem";
import MyCard from "./MyCard";

const AnswersList = ({
                         question, sOUsername, answers, newAnswer,
                         onDeleteAnswer, onEditPress, onChangeNewAnswer, onEditSubmit, onViewAnswers,
                         onChange, onCreate, onLogout, onVoteQuestion, onVoteAnswer
                     }) => (
    <div>
        <MyNavbar searchBar={false} loggedInAsButton={true} logoutButton={true} sOUsername={sOUsername}
                  onLogout={onLogout}/>

        <br/>

        <div className="container" style={{"paddingTop": "5em"}}>

            <div>
                <div className={"card myCard"}>
                    <div className="card-body">
                        <div className="row">
                            <div className="col-sm-1">
                                <MyVoteSystem qAType={"question"}
                                              sOUserName={sOUsername}
                                              questionAnswer={question}
                                              onVote={onVoteQuestion}/>
                            </div>
                            <div className="col-sm-11" style={{"paddingTop": "1em"}}>
                                <MyCard qAType="question"
                                        questionAnswer={question}
                                        onViewAnswers={onViewAnswers}/>
                                <br/>
                            </div>
                        </div>
                    </div>
                </div>
                <br/>
                <br/>
            </div>


            {

                answers.sort(function (a1, a2) {
                    return a2.score - a1.score;
                }).map((answer, index) => (
                    <div key={index}>
                        <div className={"card"}>
                            <div className="card-body">
                                <div className="row">
                                    <div className="col-sm-1">
                                        <MyVoteSystem qAType={"answer"}
                                                      sOUserName={sOUsername}
                                                      questionAnswer={answer}
                                                      onVote={onVoteAnswer}/>
                                    </div>
                                    <div className="col-sm-11" style={{"paddingTop": "1em"}}>
                                        <MyCard qAType="answer"
                                                questionId={question.id}
                                                questionAnswer={answer}
                                                sOUsername={sOUsername}
                                                onChangeNewAnswer={onChangeNewAnswer}
                                                onEditPress={onEditPress}
                                                onEditSubmit={onEditSubmit}
                                                onDeleteAnswer={onDeleteAnswer}/>
                                        <br/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br/>
                    </div>
                ))
            }


            <div className="container askQuestionForm" style={{"paddingBottom": "3em"}}>
                <form>
                    <div className="form-group row">
                        <label className="col-sm-3 col-form-label">Answer Text</label>
                        <div className="col-sm-9">
                            <textarea
                                value={newAnswer.text}
                                onChange={e => onChange("text", e.target.value)}
                                className="form-control" placeholder="Type your answer"
                                rows="3" required/>
                        </div>
                    </div>
                </form>
                <button disabled={!(newAnswer.text.length)}
                        onClick={() => onCreate(question.id)}
                        className="btn btn-block answerSubmitButton">Submit
                </button>
            </div>
        </div>
    </div>
);

export default AnswersList;