import React, {Component} from 'react';
import sOUserModel from '../../model/sOUserModel';
import LoginRegister from '../dumb/LoginRegister';
import sOUserPresenter from '../../presenter/sOUserPresenter';

const mapModelStateToComponentState = (sOUserModel) => ({
    sOUsers: sOUserModel.sOUsers,
    newSOUser: sOUserModel.newSOUser,
    loginFormOpen: sOUserModel.loginFormOpen,
    registerFormOpen: sOUserModel.registerFormOpen,
    nameAlreadyExists: sOUserModel.nameAlreadyExists,
    invalidNameOrPassword: sOUserModel.invalidNameOrPassword
});

export default class SmartLoginRegister extends Component {
    constructor(props) {
        super(props);

        this.state = mapModelStateToComponentState(sOUserModel.state);

        this.listener = (modelState) =>
            this.setState(mapModelStateToComponentState(modelState));
        sOUserModel.addListener("change", this.listener);
    }

    // prevent memory leak
    componentWillUnmount() {
        sOUserModel.removeListener("change", this.listener);
    }

    render() {
        return <LoginRegister
            toggleLogin={sOUserPresenter.toggleLogin}
            toggleRegister={sOUserPresenter.toggleRegister}
            loginFormOpen={this.state.loginFormOpen}
            registerFormOpen={this.state.registerFormOpen}

            sOUsers={this.state.sOUsers}
            newSOUser={this.state.newSOUser}
            nameAlreadyExists={this.state.nameAlreadyExists}
            invalidNameOrPassword={this.state.invalidNameOrPassword}

            onRegister={sOUserPresenter.onRegister}
            onLogin={sOUserPresenter.onLogin}
            onChange={sOUserPresenter.onChange}
            checkForSOUsername={sOUserPresenter.checkForSOUsername}

        />
    }
}