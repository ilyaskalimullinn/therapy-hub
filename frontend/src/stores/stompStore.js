import { defineStore } from "pinia";
import { useUserStore } from "./userStore";
import Stomp from "stompjs";
import sockjs from "sockjs-client/dist/sockjs";
import { apiGetChatInfo } from "@/services/api";
import { useChatStore } from "./chatStore";

export const useStompStore = defineStore('stompStore', {
    state: () => ({
        chatId: null,
        participant: null,
        sockJs: null,
        stompClient: null,
    }),
    actions: {
        async connect(chatId) {
            const chatData = await apiGetChatInfo(chatId);
            this.participant = chatData["participant"];
            this.chatId = chatId;
            this.sockJs = new sockjs("http://localhost:8080/ws");
            this.stompClient = Stomp.over(this.sockJs);
            this.stompClient.connect({Authorization: this.getAuthorizationHeader()}, this.onConnected, this.onError);
        },
        onConnected() {
            this.stompClient.subscribe(
                `/user/${this.chatId}-${useUserStore().user.id}/queue/messages`,
                this.onMessageReceived
            );
        },
        onMessageReceived(msg) {
            console.log(msg);
            msg = JSON.parse(msg.body);

            switch (msg.type) {
                case "userMessage":
                    useChatStore().messages.push(msg);
                    break;
            }
        },
        onError(error) {
            console.err(error);
        },
        sendUserMessage(messageBody) {
            if (messageBody.trim() === "") {
                return;
            }
            const message = {
                type: "userMessage",
                data: {
                    receiverId: this.participant.id,
                    senderId: 1,
                    chatId: this.chatId,
                    body: messageBody
                }
            };

            this.stompClient.send("/app/chat", {}, JSON.stringify(message));
        },
        getAuthorizationHeader() {
            return `Bearer ${useUserStore().token}`
        }

    }
})

