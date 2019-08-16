import React from 'react';
import MyNavbar from "./MyNavbar";
import MyVoteSystem from "./MyVoteSystem";
import MyCard from "./MyCard";


const FilteredQuestions = ({
                               sOUsername, filteredQuestions, filterTitle, filterText,
                               onViewAnswers, onVote
                           }) => (
    <div>
        <MyNavbar searchBar={false} loggedInAsButton={true} logoutButton={true} sOUsername={sOUsername}/>

        <br/>

        <div className="container">
            <div>
                <div className="jumbotron h-50 p-4">
                    <h5 className="display-4">
                        Questions filtered by {filterTitle ? <span>title</span> : <span>tag</span>} "{filterText}":
                    </h5>
                </div>
                <br/>

                {
                    filteredQuestions.sort(function (q1, q2) {
                        return -q1.creationDate.localeCompare(q2.creationDate);
                    }).map((question, index) => ( // assign a key for each element
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
                                                    score={question.score}
                                                    questionAnswer={question}
                                                    onViewAnswers={onViewAnswers}/>
                                            <br/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br/>
                        </div>
                    ))
                }
            </div>
        </div>
    </div>
);


export default FilteredQuestions;