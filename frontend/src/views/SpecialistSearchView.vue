<script setup>
import MainLayout from "../components/blocks/MainLayout.vue";
import SpecialistSearchForm from "../components/forms/SpecialistSearchForm.vue";
</script>

<template>
  <MainLayout>
    <div class="about">
      <h1>Search for specialist</h1>
      <SpecialistSearchForm>

      </SpecialistSearchForm>
    </div>
  </MainLayout>
</template>


<script>
export default {
  name: "SpecialistSearchView",
  components: {MainLayout}
}
</script>
