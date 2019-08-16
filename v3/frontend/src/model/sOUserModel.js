import {EventEmitter} from "events";
import RestClient from "../rest/RestClient";
import WebSocketListener from "../websocket/WebSocketListener";
import questionModel from "./questionModel";
import answerModel from "./answerModel";

let client = new RestClient();
let wsListener = new WebSocketListener();
wsListener.client.deactivate();

export function getClient() {
    return client;
}

class SOUserModel extends EventEmitter {
    constructor() {
        super();
        this.state = {
            sOUsers: [],
            newSOUser: {
                id: -1,
                soUsername: "",
                soPassword: ""
            },
            loggedInSOUser: "Hacker",
            invalidNameOrPassword: false,
            nameAlreadyExists: false,
            loginFormOpen: false,
            registerFormOpen: false
        }
    }

    registerSOUser(soUsername, soPassword) {
        client = new RestClient(soUsername, soPassword);
        wsListener.client.deactivate();
        wsListener = new WebSocketListener(soUsername, soPassword);
        this.onEvent(wsListener);
        return client.createSOUser(soUsername, soPassword)
            .then(sOUser => this.updateUsersAfterRegister(sOUser));
    }

    updateUsersAfterRegister(sOUser) {
        this.state = {
            ...this.state,
            sOUsers: this.state.sOUsers.concat([sOUser])
        };
        this.emit("change", this.state);
    }

    loginSOUser(soUsername, soPassword) {
        client = new RestClient(soUsername, soPassword);
        wsListener.client.deactivate();
        wsListener = new WebSocketListener(soUsername, soPassword);
        this.onEvent(wsListener);
        return client.loginSOUser(soUsername, soPassword)
            .then(response => {
                    if (response.ok) {
                        this.changeMainStateProperty("invalidNameOrPassword", false);
                        response = response.json();
                        //this.updateUsersAfterRegister(response)
                    } else {
                        this.changeMainStateProperty("invalidNameOrPassword", true);
                    }
                }
            );
    }

    onEvent(wsListener) {
        wsListener.on("event", event => {
            // the event object is the one from backend, has a DTO and an inherited type
            if (event.type === "USER_CREATED") {
                //alert("INTRI AICI?");
                this.updateUsersAfterRegister(event.soUser)
            }
            if (event.type === "QUESTION_CREATED") {
                questionModel.appendQuestion(event.question)
            }
            if (event.type === "ANSWER_CREATED") {
                answerModel.appendAnswer(event.answer)
            }
            if (event.type === "ANSWER_EDITED") {
                answerModel.modifyTextForAnswer(event.answer)
            }
            if (event.type === "ANSWER_DELETED") {
                answerModel.updateAnswersAfterDelete(event.answerDeletedId)
            }
        });
    }

    loadSOUsers() {
        return client.loadAllSOUsers()
            .then(sOUsers => {
                this.state = {
                    ...this.state,
                    sOUsers: sOUsers
                };
                this.emit("change", this.state);
            });
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

    logout() {
        questionModel.clearQuestions();
        this.changeMainStateProperty("loggedInSOUser", "Hacker");
        window.location.assign("#/");
        client = new RestClient();
        wsListener.client.deactivate();
    };


    nameExists(sOUsername) {
        let users = this.state.sOUsers;
        for (let i = 0; i < users.length; i++) {
            if (users[i].soUsername === sOUsername) {
                return true;
            }
        }
        return false;
    }

    getSOUserId(soUsername) {
        let users = this.state.sOUsers;
        for (let i = 0; i < users.length; i++) {
            if (users[i].soUsername === soUsername) {
                return users[i].id;
            }
        }
        return -1;
    }

    getSOUsername(soUserId) {
        let users = this.state.sOUsers;
        for (let i = 0; i < users.length; i++) {
            if (users[i].id === soUserId) {
                return users[i].soUsername;
            }
        }
        return "error";
    }

}


const sOUserModel = new SOUserModel();


export default sOUserModel;