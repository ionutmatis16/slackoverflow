import {EventEmitter} from "events";

class SOUserModel extends EventEmitter {
    constructor() {
        super();
        this.state = {
            sOUsers: [{
                sOUsername: "b",
                sOPassword: "b"
            }, {
                sOUsername: "u2",
                sOPassword: "p2"
            }],
            newSOUser: {
                sOUsername: "",
                sOPassword: ""
            },
            loggedInSOUser: "Hacker",
            invalidNameOrPassword: false,
            nameAlreadyExists: false,
            loginFormOpen: false,
            registerFormOpen: false
        }
    }

    addSOUser(sOUsername, sOPassword) {
        this.state = {
            ...this.state,
            sOUsers: this.state.sOUsers.concat([{
                sOUsername: sOUsername,
                sOPassword: sOPassword
            }])
        };
        this.emit("change", this.state);
    }

    changeNewSOUserProperty(property, value) {
        this.state = {
            ...this.state, // contains all the properties of the old state, copied
            newSOUser: {
                ...this.state.newSOUser,
                [property]: value // the property contained will be changed
            }
        };
        this.emit("change", this.state); // the state has changed, passed the new state as arg
    }

    changeMainStateProperty(property, value) {
        this.state = {
            ...this.state,
            [property]: value
        };
        this.emit("change", this.state);
    }

    loginSuccess(username, password) {
        let users = this.state.sOUsers;
        for (let i = 0; i < users.length; i++) {
            if (users[i].sOUsername === username && users[i].sOPassword === password) {
                return true;
            }
        }
        return false;
    }

    nameExists(sOUsername) {
        let users = this.state.sOUsers;
        for (let i = 0; i < users.length; i++) {
            if (users[i].sOUsername === sOUsername) {
                return true;
            }
        }
        return false;
    }


}

const sOUserModel = new SOUserModel();

export default sOUserModel;