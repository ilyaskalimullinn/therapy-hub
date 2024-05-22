<template>
    <form action="POST" @submit.prevent="this.submit()" class="form">
        <div class="title">
            Update profile?
        </div>

        <DefaultLoader v-if="specialtyStoreRequestData.loading" />
        <div class="form-body" v-else>
            <div class="common">
                <div class="fullName">
                    <label for="fullName">Your name:</label>
                    <input type="text" name="fullName" id="fullName" v-model="form.common.fullName">
                </div>
            </div>

            <div class="specialist" v-if="user.role === 'SPECIALIST'">
                <div class="bio">
                    <label for="bio">Your bio:</label>
                    <input type="text" name="bio" id="bio" v-model="form.specialist.bio">
                </div>
                <div class="price">
                    <label for="price">Price:</label>
                    <input type="number" min="0" name="price" id="price" v-model="form.specialist.price">
                </div>
                <div class="specialties">
                    <p>Specialties: </p>
                    <div class="specialty" v-for="specialty in allSpecialties" v-bind:key="specialty.id">
                        <label :for="`specialty-${specialty.id}`">{{ specialty.name }}</label>
                        <input type="checkbox" name="specialties" :id="`specialty-${specialty.id}`" :value="specialty"
                            v-model="form.specialist.specialties" />
                    </div>
                </div>
            </div>

        </div>

        <DefaultLoader v-if="userStoreRequestData.loading" />
        <FormSubmit value="Update profile" v-else />
    </form>
</template>

<script>
import { useSpecialtyStore } from "../../stores/specialtyStore.js";
import { useUserStore } from "../../stores/userStore.js";
import { mapActions, mapState } from "pinia";
import FormSubmit from "./FormSubmit.vue";
import DefaultLoader from "../utils/DefaultLoader.vue";
import { UpdateProfileDto } from "../../models/dto.js";

export default {
    name: "UpdateSpecialtiesForm",
    components: { FormSubmit, DefaultLoader },
    data() {
        return {
            form: {
                common: {
                    fullName: useUserStore().user.fullName
                },
                specialist: {
                    bio: useUserStore().user.bio,
                    price: useUserStore().user.price,
                    specialties: useUserStore().user.specialties
                }
            }
        }
    },
    computed: {
        ...mapState(useSpecialtyStore, {
            specialtyStoreRequestData: (state) => state.requestData,
            allSpecialties: (state) => state.specialtyList,
        }),
        ...mapState(useUserStore, {
            userStoreRequestData: (state) => state.requestData,
            user: (state) => state.user
        })
    },
    async beforeMount() {
        await this.ensureLoaded();
    },
    methods: {
        ...mapActions(useSpecialtyStore, ['ensureLoaded']),
        ...mapActions(useUserStore, ['updateProfile']),
        async submit() {
            let updateProfileDto = UpdateProfileDto.fromForm(this.form);
            console.log(updateProfileDto);
            await this.updateProfile(updateProfileDto, this.form.specialist.specialties);
        },
    }
}
</script>

<style scoped>
.form {
    border: 1px solid black;
}
</style>
