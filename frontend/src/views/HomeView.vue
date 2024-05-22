<script setup>
</script>

<template>
  <MainLayout>
    <h1>Home page</h1>
    <h2>Начните работу с собой прямо сейчас</h2>
    <div>
      <p>Воспользуйтесь фильтром поиском для подбора психолога, который поймет вас</p>
      <RouterLink :to="{name: 'SpecialistSearch'}">Начать</RouterLink>
    </div>
    <h2>Дополнительные материалы для самостоятельной работы:</h2>
    <ul>
      <li><a :href="lessons_url">Уроки медитаций</a></li>
      <li><a :href="tests_url">Психологические тесты</a></li>
    </ul>
  </MainLayout>
</template>

<script>
import MainLayout from "../components/blocks/MainLayout.vue";
import { LESSONS_URL, TESTS_URL } from "../services/consts.js";


export default {
  name: "HomeView",
  components: {MainLayout},
  data() {
    return {
      lessons_url: LESSONS_URL,
      tests_url: TESTS_URL
    }
  }
}
</script>
