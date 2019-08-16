import sOUserModel from "../model/sOUserModel";

class SOUserPresenter {
    onLogin = () => {
        let newSOUser = sOUserModel.state.newSOUser;
        sOUserModel.loginSOUser(newSOUser.soUsername, newSOUser.soPassword)
            .then(() => {
                    sOUserModel.changeNewSOUserProperty("soUsername", "");
                    sOUserModel.changeNewSOUserProperty("soPassword", "");

                    if (!sOUserModel.state.invalidNameOrPassword) {
                        sOUserModel.changeMainStateProperty("loggedInSOUser", newSOUser.soUsername);
                        sOUserModel.changeMainStateProperty("invalidNameOrPassword", false);
                        window.location.assign("#/questions");
                    } else {
                        sOUserModel.changeMainStateProperty("invalidNameOrPassword", true);
                        window.location.assign("#/");
                    }
                }
            );
    };

    onLogout = () => {
        sOUserModel.logout();
    };

    onRegister = () => {
        let newSOUser = sOUserModel.state.newSOUser;

        if (!sOUserModel.nameExists(newSOUser.soUsername)) {
            sOUserModel.registerSOUser(newSOUser.soUsername, newSOUser.soPassword)
                .then(() => {
                    sOUserModel.changeNewSOUserProperty("soUsername", "");
                    sOUserModel.changeNewSOUserProperty("soPassword", "");
                    sOUserModel.changeMainStateProperty("loggedInSOUser", newSOUser.soUsername);
                    window.location.assign("#/questions");
                });
        }

    };

    onChange = (property, value) => {
        sOUserModel.changeNewSOUserProperty(property, value);
    };

    toggleLogin = () => {
        let loginOn = !sOUserModel.state.loginFormOpen;
        let registerOn = sOUserModel.state.registerFormOpen;
        if (registerOn)
            registerOn = !registerOn;
        sOUserModel.changeMainStateProperty("loginFormOpen", loginOn);
        sOUserModel.changeMainStateProperty("registerFormOpen", registerOn);
        sOUserModel.changeNewSOUserProperty("soUsername", "");
        sOUserModel.changeNewSOUserProperty("soPassword", "");
    };

    toggleRegister = () => {
        let loginOn = sOUserModel.state.loginFormOpen;
        let registerOn = !sOUserModel.state.registerFormOpen;
        if (loginOn)
            loginOn = !loginOn;
        sOUserModel.changeMainStateProperty("loginFormOpen", loginOn);
        sOUserModel.changeMainStateProperty("registerFormOpen", registerOn);
        sOUserModel.changeNewSOUserProperty("soUsername", "");
        sOUserModel.changeNewSOUserProperty("soPassword", "");
    };

    checkForSOUsername = (sOUsername) => {
        let foundName = sOUserModel.nameExists(sOUsername);
        foundName ?
            sOUserModel.changeMainStateProperty("nameAlreadyExists", true)
            :
            sOUserModel.changeMainStateProperty("nameAlreadyExists", false);
    };

    onInit = () => {
        sOUserModel.loadSOUsers();
    }
}

const sOUserPresenter = new SOUserPresenter();

export default sOUserPresenter;