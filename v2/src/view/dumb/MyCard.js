import React from 'react';

const MyCard = ({
                    qAType, questionAnswer, sOUsername,
                    onViewAnswers, onDeleteAnswer, onEditPress,
                    onChangeNewAnswer, onEditSubmit
                }) => (
    <div>
        {
            qAType === "question" ?
                <h5 className="card-title d-inline-block">
                    <strong className="fakeLink"
                            onClick={() => onViewAnswers(questionAnswer.id)}>{questionAnswer.title} </strong>

                </h5>
                :
                (
                    questionAnswer.userName === sOUsername ?
                        <div>
                            <button onClick={() => onDeleteAnswer(questionAnswer.id)}
                                    className="btn btn-sm btn-danger float-right"
                                    style={{"margin-left": "1em"}}>
                                Delete
                            </button>
                            <div style={{"margin-right": "1em"}}/>
                            <button onClick={() => onEditPress(questionAnswer.id)} type="button"
                                    className="btn btn-sm btn-info float-right">
                                Edit
                            </button>
                            <br/>
                            <br/>
                        </div>
                        :
                        <div/>
                )
        }


        <h6 className="card-title d-inline-block float-right"> {questionAnswer.creationDate} </h6>
        <p className="card-text">{questionAnswer.text}</p>
        {
            qAType === "question" ?
                questionAnswer.tags.map((tag, index) =>
                    <button key={index}
                            className="btn btn-sm tagButton d-inline">{tag}</button>)
                :
                <div/>
        }
        <p className="d-inline float-right">
            Posted by <strong className="fakeLink"
                              style={{"color": "#8fc1e3"}}>{questionAnswer.userName}</strong>
        </p>
        <br/>
        {
            qAType === "answer" ?
                questionAnswer.editButtonPressed ?
                    <div className="input-group mb-3">
                        <div className="input-group-prepend">
                            <label className="input-group-text">New text</label>
                        </div>

                        <input value={questionAnswer.newAnswerText}
                               onChange={e => onChangeNewAnswer(questionAnswer.id, e.target.value)}
                               type="text" className="form-control" placeholder="Your new text"
                               aria-label="New text" aria-describedby="basic-addon2"/>
                        <div className="input-group-append">
                            <button onClick={() => onEditSubmit(questionAnswer.id)}
                                    className="btn btn-outline-secondary"
                                    disabled={!(questionAnswer.newAnswerText.length > 0)}
                                    type="button">Submit
                            </button>
                        </div>
                    </div>
                    :
                    <div/>
                :
                <div/>
        }
    </div>
);

export default MyCard;