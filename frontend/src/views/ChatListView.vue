<template>
    <MainLayout>
        <h1>Chat List</h1>

        <DefaultLoader v-if="requestData.loading" />

        <div class="chat-list" v-else>

            <div class="chat" v-for="chat in chats" v-bind:key="chat.id">
                <RouterLink :to="{name: 'Chat', params: {id: chat.id}}">
                    Chat with {{chat.participant.fullName}}
                </RouterLink>
            </div>

        </div>
    </MainLayout>
</template>

<script>
import { mapActions, mapState } from "pinia";
import MainLayout from "../components/blocks/MainLayout.vue";
import { useChatStore } from "../stores/chatStore.js";
import DefaultLoader from "../components/utils/DefaultLoader.vue";

export default {
    name: "ChatList",
    components: { MainLayout, DefaultLoader },
    async mounted() {
        this.fetchChats();
    },
    computed: {
        ...mapState(useChatStore, ["chats"])
    },
    methods: {
        ...mapActions(useChatStore, ["fetchChats", "requestData"])
    }
}
</script>
