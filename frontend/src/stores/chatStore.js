import { defineStore } from "pinia";
import { RequestData } from "@/models/util.js";
import { apiFetchChats, apiCreateChat } from "@/services/api";

export const useChatStore = defineStore('chatStore', {
    state: () => ({
        chats: [],
        messages: [],
        requestData: new RequestData(false, null),
    }),
    actions: {
        async fetchChats() {
            this.requestData.startLoading();

            try {
                this.chats = await apiFetchChats();
            } catch (error) {
                this.requestData.setError(error);
            }

            this.requestData.stopLoading();
        },
        async createChat(createChatDto) {
            this.requestData.startLoading();

            try {
                const data = await apiCreateChat(createChatDto);
                return data["chatId"];
            } catch (error) {
                this.requestData.setError(error);
            }

            this.requestData.stopLoading();
            return null;
        },
        clearMessages() {
            this.messages = [];
        }
    }
})
