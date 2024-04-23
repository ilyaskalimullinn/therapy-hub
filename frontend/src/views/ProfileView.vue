<script setup>
import { useUserStore } from "../stores/userStore";
import { mapState } from "pinia";
</script>

<template>
    <MainLayout>
        <h1>Profile</h1>

        <h2>{{this.user.fullName}}</h2>
        <h2>{{ this.user.username }}</h2>

        <h2>{{ this.rolePretty }}</h2>


        <div v-if="this.user.role === 'specialist'">
            <h2>Специальность</h2>
            <ul>
                <li v-for="specialty in this.user.specialtyList" :key="specialty.id">
                    {{ specialty.name }}
                </li>
            </ul>
        </div>
    </MainLayout>
</template>

<script>
import MainLayout from "../components/blocks/MainLayout.vue";

export default {
    name: "ProfileView",
    computed: {
        ...mapState(useUserStore, {
            error: (state) => state.requestData.error,
            user: 'user', 
            rolePretty: 'rolePretty',
        })
    },
}

</script>
