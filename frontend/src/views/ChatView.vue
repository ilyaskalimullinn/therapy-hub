<template>
    <MainLayout>
        <h1>Чат</h1>

        <div class="wrapper">
            <h2 v-if="participant !== null">Вы общаетесь с пользователем {{ participant.fullName }}</h2>
            <div class="messages">
                <MessageBlock :message="message" v-for="message in messages" v-bind:key="message.id" />
            </div>

            <form action="" @submit.prevent="submit()">
                <label for="message-body">Message: </label>
                <input type="text" name="message" v-model="messageBody" id="message-body">

                <input type="submit" value="Send" />
            </form>
        </div>

    </MainLayout>
</template>

<script>
import { mapActions, mapState } from "pinia";
import MainLayout from "../components/blocks/MainLayout.vue";
import MessageBlock from "../components/blocks/MessageBlock.vue"
import { useStompStore } from "../stores/stompStore.js";
import { useChatStore } from "../stores/chatStore.js";

export default {
    name: "ChatView",
    components: { MainLayout, MessageBlock },
    data() {
        return {
            messageBody: ""
        }
    },
    async mounted() {
        this.connect(parseInt(this.$route.params["id"]));
    },
    methods: {
        submit() {
            this.sendUserMessage(this.messageBody);
            this.messageBody = "";
        },
        ...mapActions(useStompStore, ["connect", "sendUserMessage"])
    },
    computed: {
        ...mapState(useChatStore, ["messages"]),
        ...mapState(useStompStore, ["participant"])
    }
}
</script>

<style scoped>

.wrapper {
    margin: 0 auto;
    width: 70%;
}
</style>
