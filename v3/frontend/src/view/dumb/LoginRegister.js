import React from 'react';
import MyNavbar from "./MyNavbar";
import '../../App.css';
import {Button, Card, CardBody, Collapse} from 'reactstrap';


const LoginRegister = ({
                           toggleLogin, toggleRegister, loginFormOpen, registerFormOpen,
                           sOUsers, newSOUser, nameAlreadyExists, checkForSOUsername, invalidNameOrPassword,
                           onChange, onRegister, onLogin
                       }) => (
    <div>
        <MyNavbar searchBar={false} loggedInAsButton={false} logoutButton={false}/>

        <div className="container">
            <div className="jumbotron h-75 p-5">
                <h1 className="display-4"><strong>Welcome to SlackOverflow! </strong></h1>
                <p className="lead">This website represent the second assignment for Software Design laboratory.</p>
                <hr className="my-4"/>
                <p className="lead">To continue please <Button onClick={toggleLogin}
                                                               className="btn myButton">Login</Button>or <Button
                    onClick={toggleRegister} className="btn myButton"> Register</Button></p>

                <div>
                    <Collapse isOpen={loginFormOpen}>
                        <Card>
                            <CardBody>
                                <div className="container askQuestionForm">
                                    <form>
                                        <div className="form-group row">
                                            <label className="col-sm-3 col-form-label">Username</label>
                                            <div className="col-sm-9">
                                                <input value={newSOUser.soUsername}
                                                       onChange={e => onChange("soUsername", e.target.value)}
                                                       type="text" className="form-control"
                                                       placeholder="Enter your username" required/>
                                            </div>
                                        </div>
                                        <div className="form-group row">
                                            <label className="col-sm-3 col-form-label">Password</label>
                                            <div className="col-sm-9">
                                                <input value={newSOUser.soPassword}
                                                       onChange={e => onChange("soPassword", e.target.value)}
                                                       type="password" className="form-control"
                                                       placeholder="Enter your password" required/>
                                            </div>
                                        </div>
                                    </form>
                                    <button id="loginButton"
                                            disabled={!(newSOUser.soUsername.length > 0 && newSOUser.soPassword.length > 0)}
                                            onClick={onLogin}
                                            className="btn btn-block answerSubmitButton">Login
                                    </button>
                                    {
                                        invalidNameOrPassword ?
                                            <div>
                                                <br/>
                                                <div className="alert alert-danger" role="alert">
                                                    Invalid name or password!
                                                </div>
                                            </div>
                                            :
                                            <div/>
                                    }
                                </div>
                            </CardBody>
                        </Card>
                    </Collapse>

                    <Collapse isOpen={registerFormOpen}>
                        <Card>
                            <CardBody>
                                <div className="container askQuestionForm">
                                    <form>
                                        <div className="form-group row">
                                            <label className="col-sm-3 col-form-label">Username</label>
                                            <div className="col-sm-9">
                                                <input value={newSOUser.soUsername}
                                                       onChange={e => {
                                                           onChange("soUsername", e.target.value);
                                                           checkForSOUsername(e.target.value)
                                                       }}
                                                       type="text" className="form-control"
                                                       placeholder="Choose an username" required/>
                                            </div>
                                        </div>
                                        <div className="form-group row">
                                            <label className="col-sm-3 col-form-label">Password</label>
                                            <div className="col-sm-9">
                                                <input value={newSOUser.soPassword}
                                                       onChange={e => onChange("soPassword", e.target.value)}
                                                       type="password" className="form-control"
                                                       placeholder="Choose a password" required/>
                                            </div>
                                        </div>
                                    </form>
                                    <button
                                        disabled={!(newSOUser.soPassword.length > 0 &&
                                            newSOUser.soUsername.length > 0 &&
                                            !nameAlreadyExists)}
                                        onClick={onRegister} className="btn btn-block answerSubmitButton">Register
                                    </button>
                                    {
                                        nameAlreadyExists ?
                                            <div>
                                                <br/>
                                                <div className="alert alert-danger" role="alert">
                                                    Username already exists!
                                                </div>
                                            </div>
                                            :
                                            <div/>
                                    }
                                </div>
                            </CardBody>
                        </Card>
                    </Collapse>
                </div>
            </div>
        </div>
    </div>
);


export default LoginRegister;