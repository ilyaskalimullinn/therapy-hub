<template>
    <MainLayout>
        <h1>CHAT</h1>
    </MainLayout>
</template>

<script>
import { mapActions } from "pinia";
import MainLayout from "../components/blocks/MainLayout.vue";
import { useStompStore } from "../stores/stompStore.js";

export default {
    name: "ChatView",
    components: { MainLayout },
    async mounted() {
        console.log("HELLO");
        this.connect();

        // this.sendMessage("test");
    },
    methods: {
        ...mapActions(useStompStore, ["connect", "sendMessage"])
    }
}
</script>
