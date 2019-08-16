import {EventEmitter} from "events";
import {Client} from "@stomp/stompjs";

export default class WebSocketListener extends EventEmitter {

    constructor(soUsername, soPassword) {
        super();
        this.client = new Client({
            // websocket protocol
            brokerURL: "ws://" + soUsername + ":" + soPassword
                // + host of our server + endpoint we configured on backend
                + "@localhost:8080/api/websocket",
            onConnect: () => {
                // corresponds to the one from backend
                this.client.subscribe("/topic/events", message => {
                    this.emit("event", JSON.parse(message.body));
                })
            }
        });
        // start listening to the websocket
        this.client.activate();
    }
}