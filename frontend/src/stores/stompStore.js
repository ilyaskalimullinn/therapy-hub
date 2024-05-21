import { defineStore } from "pinia";
import { useUserStore } from "./userStore";
import Stomp from "stompjs";
import sockjs from "sockjs-client/dist/sockjs";

export const useStompStore = defineStore('stompStore', {
    state: () => ({
        sockJs: null,
        stompClient: null,
    }),
    actions: {
        connect() {
            this.sockJs = new sockjs("http://localhost:8080/ws");
            this.stompClient = Stomp.over(this.sockJs);
            this.stompClient.connect({"Authorization": this.getAuthorizationHeader()}, this.onConnected, this.onError);
        },
        onConnected() {
            this.stompClient.subscribe(
                "/user/" + useUserStore().user.id + "/queue/messages",
                this.onMessageReceived
            );
        },
        onMessageReceived(msg) {
            console.log(msg);
        },
        onError(error) {
            console.err(error);
        },
        sendMessage(msg) {
            if (msg.trim() !== "") {
                const message = {
                    receiverId: 2,
                    body: msg
                };

                this.stompClient.send("/app/chat", {}, JSON.stringify(message));
            }
        },
        getAuthorizationHeader() {
            return `Bearer ${useUserStore().token}`
        }

    }
})

