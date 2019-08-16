import React from 'react';
import './App.css';
import {HashRouter, Route, Switch} from "react-router-dom";
import SmartLoginRegister from "./view/smart/SmartLoginRegister";
import SmartQuestionsList from "./view/smart/SmartQuestionsList";
import SmartAnswersList from "./view/smart/SmartAnswersList";
import SmartFilteredQuestions from "./view/smart/SmartFilteredQuestions";

const App = () => (
    <div className="App">
        <HashRouter>
            <Switch>
                <Route exact component={SmartLoginRegister} path="/"/>
                <Route exact component={SmartQuestionsList} path="/questions"/>
                <Route exact component={SmartFilteredQuestions} path="/questions/filter"/>
                <Route exact component={SmartAnswersList} path="/question/:id"/>
            </Switch>
        </HashRouter>
    </div>
);


export default App;
