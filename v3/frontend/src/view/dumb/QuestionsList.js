// DUMB Component
import React from 'react';
import '../../App.css';
import {Card, CardBody, UncontrolledCollapse} from 'reactstrap';
import MyNavbar from './MyNavbar';
import MyVoteSystem from "./MyVoteSystem";
import MyCard from "./MyCard";

const QuestionsList = ({
                           questions, newQuestion, onCreate, onChange, sOUsername, filterTitle,
                           onChangeFilter, onSearchClick, filterText, onSearchChange,
                           onViewAnswers, onLogout, allTags, onAvailableTagsClick, onVote
                       }) => {

    return (
        <div>

            <MyNavbar searchBar={true} loggedInAsButton={true} logoutButton={true} sOUsername={sOUsername}
                      filterTitle={filterTitle} onChangeFilter={onChangeFilter} onSearchClick={onSearchClick}
                      filterText={filterText} onSearchChange={onSearchChange} onLogout={onLogout}/>

            <br/>

            <div className="container" style={{"paddingTop": "4em"}}>

                <div>
                    <div className="d-flex justify-content-center" style={{"paddingBottom": "20px"}}>
                        <div className="my-auto">
                            <button id="toggler" className="align-items-center btn askButtonClass">Ask a question
                            </button>
                        </div>
                    </div>

                    <UncontrolledCollapse toggler="#toggler">
                        <Card>
                            <CardBody>
                                <div className="container askQuestionForm">
                                    <form>
                                        <div className="form-group row">
                                            <label className="col-sm-3 col-form-label">Question Title</label>
                                            <div className="col-sm-9">
                                                <input value={newQuestion.title}
                                                       onChange={e => onChange("title", e.target.value)}
                                                       type="text" className="form-control"
                                                       placeholder="Type the title" required/>
                                            </div>
                                        </div>
                                        <div className="form-group row">
                                            <label className="col-sm-3 col-form-label">Question Text</label>
                                            <div className="col-sm-9">
                                                    <textarea value={newQuestion.text}
                                                              onChange={e => onChange("text", e.target.value)}
                                                              className="form-control" placeholder="Type your question"
                                                              rows="3" required/></div>
                                        </div>
                                        <div className="form-group row">
                                            <label className="col-sm-3 col-form-label">Question tags</label>
                                            <div className="col-sm-9">
                                                <input value={newQuestion.tags}
                                                       onChange={e => onChange("tags", e.target.value)}
                                                       type="text" className="form-control"
                                                       placeholder="Enter the tags separated by space (new or existent)"
                                                       required/>
                                            </div>
                                        </div>

                                        <div className="row">
                                            <label className="col-sm-3">Available tags:</label>
                                            <label className="col-sm-9">
                                                {
                                                    allTags.map((tag, index) => (
                                                        <span key={index}>
                                                            <div onClick={() => onAvailableTagsClick(tag)}
                                                                 className="btn btn-sm myButton fakeLink">{tag}</div>
                                                        </span>
                                                    ))
                                                }
                                            </label>
                                        </div>

                                    </form>
                                    <button onClick={onCreate} id="toggler"
                                            style={{"marginTop": "1.5em"}}
                                            disabled={!(newQuestion.title.length > 0 && newQuestion.text.length > 0)}
                                            className="btn btn-block answerSubmitButton">Submit
                                    </button>
                                </div>
                            </CardBody>
                        </Card>
                        <br/>
                    </UncontrolledCollapse>
                </div>

                {
                    questions.map((question, index) => ( // assign a key for each element
                            <div key={index}>
                                <div className={"card"}>
                                    <div className="card-body">
                                        <div className="row">
                                            <div className="col-sm-1">
                                                <MyVoteSystem qAType={"question"}
                                                              sOUserName={sOUsername}
                                                              questionAnswer={question}
                                                              onVote={onVote}/>
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
                            </div>
                        )
                    )
                }
            </div>
        </div>
    )
};

// https://visme.co/blog/wp-content/uploads/2016/09/website30-1024x512.jpg
export default QuestionsList;