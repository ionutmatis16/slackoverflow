import React from 'react';
import {FaChevronDown, FaChevronUp} from "react-icons/fa";

const MyVoteSystem = ({questionAnswer, onVote, sOUserName}) => (
    <div style={{"-webkit-user-select": "none"}}>
        <h1 className="alignCenter vote"
            onClick={() => {
                if (questionAnswer.userName !== sOUserName)
                    return onVote(questionAnswer.id, true, sOUserName)
            }}><FaChevronUp/></h1>
        <h3 className="alignCenter">{questionAnswer.score}</h3>
        <h1 className="alignCenter vote"
            onClick={() => {
                if (questionAnswer.userName !== sOUserName)
                    return onVote(questionAnswer.id, false, sOUserName)
            }}><FaChevronDown/></h1>
    </div>
);

export default MyVoteSystem;