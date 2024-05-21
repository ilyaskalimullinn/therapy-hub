<template>
    <form action="POST" @submit.prevent="this.submit()" class="form">
        <DefaultLoader v-if="requestData.loading" />
        <div class="error" v-else-if="requestData.error !== null">
            {{ error.message }}
        </div>
        <FormSubmit value="Write a message" v-else />
    </form>
</template>

<script>
import { useChatStore } from "../../stores/chatStore.js";
import { mapActions, mapState } from "pinia";
import { CreateChatDto } from "../../models/dto.js";
import FormSubmit from "./FormSubmit.vue";
import DefaultLoader from "../utils/DefaultLoader.vue";

export default {
    name: "StartChatForm",
    components: { FormSubmit, DefaultLoader },
    computed: {
        ...mapState(useChatStore, ["requestData", "chats"])
    },
    props: {
        participantId: Number
    },
    methods: {
        ...mapActions(useChatStore, ['fetchChats', 'createChat']),
        async submit() {
            let chatId = null;

            await this.fetchChats();

            if ((chatId = this.findChat()) === null) {
                console.log("Creating new chat")
                let createChatDto = new CreateChatDto(this.participantId);
                chatId = await this.createChat(createChatDto);
            }

            if (this.requestData.error === null) {
                this.$router.push({ name: 'Chat', params: { id: chatId } });
            }
            
        },
        findChat() {
            for (let chat of this.chats) {
                if (chat.participant.id === this.participantId) {
                    console.log("Returning existing chat ", chat.id)
                    return chat.id;
                }
            }
            return null;
        }
    }
}
</script>

<style scoped>
.form {
    border: 1px solid black;
}
</style>
