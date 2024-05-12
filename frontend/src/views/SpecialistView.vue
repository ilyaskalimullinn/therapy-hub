<template>
    <MainLayout>
        <DefaultLoader v-if="requestData.loading" />
        <div class="error" v-else-if="requestData.error !== null">
            {{ requestData.error.message }}
        </div>
        <div class="specialist-data" v-else>
            <div class="specialist">
                <h2 class="name">
                    {{ currentSpecialist.fullName }}
                </h2>
                <h3 class="bio">
                    {{ currentSpecialist.bio }}
                </h3>
                <div class="price">
                    Price: {{ currentSpecialist.price }}
                </div>

                <div class="rating">
                    Rating: {{ currentSpecialist.rating }}
                </div>
                <div class="review-list">
                    <div class="review-list-title">
                        Reviews:
                    </div>
                    <div v-for="review in currentSpecialist.reviews" v-bind:key="review.id" class="review">
                        <div class="review-client">
                            {{ review.client.fullName }}
                        </div>
                        <div class="review-rating">
                            {{ review.rating }}
                        </div>
                        <div class="review-comment">
                            {{ review.comment }}
                        </div>
                    </div>
                </div>

            </div>

            <CreateAppointmentForm :specialistId="parseInt($route.params.id)" />
        </div>
    </MainLayout>
</template>


<script>
import MainLayout from "../components/blocks/MainLayout.vue";
import { useSpecialistStore } from "../stores/specialistStore.js";
import { mapActions, mapState } from "pinia";
import DefaultLoader from "../components/utils/DefaultLoader.vue";
import CreateAppointmentForm from "../components/forms/CreateAppointmentForm.vue";

export default {
    name: "SpecialistView",
    components: { MainLayout, DefaultLoader, CreateAppointmentForm },
    async beforeMount() {
        this.fetchSpecialist(this.$route.params.id);
    },
    methods: {
        ...mapActions(useSpecialistStore, ["fetchSpecialist"]),
    },
    computed: {
        ...mapState(useSpecialistStore, {
            requestData: (state) => state.requestData,
            currentSpecialist: (state) => state.currentSpecialist,
        })
    }
}
</script>
