<template>
    <DefaultLoader v-if="requestData.loading" />
    <div class="error" v-else-if="requestData.error !== null">
        {{ requestData.error.getMessage() }}
    </div>
    <ul class="specialistList" v-else>
        <li class="specialist" v-for="specialist in specialists" v-bind:key="specialist.id">
            <div class="name">
                {{ specialist.name }}
            </div>
            <div class="bio">
                Bio: {{ specialist.bio }}
            </div>
            <div class="price">
                {{ specialist.price }} рублёв
            </div>
            <div class="rating">Рейтинг: {{ specialist.rating }}</div>
            <div class="specialtyList">
                <div>Специальности: </div>
                <ul class="specialtyListUl">
                    <li class="specialty" v-for="specialty in specialist.specialityList" v-bind:key="specialty.id">
                        {{ specialty.name }}
                    </li>
                </ul>
            </div>

        </li>
    </ul>
</template>

<script>
import { useSpecialistStore } from "../../stores/specialistStore.js";
import DefaultLoader from "../utils/DefaultLoader.vue";
import { mapState } from "pinia";
export default {
    name: "SpecialistList",
    components: { DefaultLoader },
    data() {
        return {

        }
    },
    computed: {
        ...mapState(useSpecialistStore, ["specialists", "requestData"])
    }
}
</script>

<style scoped>
.specialist {
    display: flex;
    justify-content: space-between;
}

.specialtyListUl {
    display: flex;
    flex-direction: column
}
</style>
