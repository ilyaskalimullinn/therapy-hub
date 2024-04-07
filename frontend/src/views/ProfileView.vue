<script setup>
import { useUserStore } from "../stores/userStore";
import { mapState } from "pinia";
</script>

<template>
    <main>
        <h1>Profile</h1>

        
    </main>
</template>

<script>

export default {
    name: "ProfileView",
    data() {
        return {

        }
    },
    computed: {
        ...mapState(useUserStore, {
            error: (state) => state.requestData.error,
            user: 'user',
        })
    },
}

</script>
