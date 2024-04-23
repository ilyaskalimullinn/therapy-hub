<template>
    <BaseAuthForm @submit.prevent="this.submit" :error="this.error" title="Sign up">
        <template v-slot:fields>
            <FormField name="email" v-model="this.form.email" :errors="v$.form.email.$errors" label="Email" type="email"
                required />

            <FormField name="fullName" v-model="this.form.fullName" :errors="v$.form.fullName.$errors" label="Name"
                required />

            <FormField name="password" v-model="this.form.password" :errors="v$.form.password.$errors" label="Password"
                type="password" required />

            <FormField name="passwordRepeat" v-model="this.form.passwordRepeat" :errors="v$.form.passwordRepeat.$errors"
                label="Password again" type="password" required />

            <div>
                <div>
                    <input type="radio" name="role" id="role-client" value="CLIENT" v-model="this.form.role" checked>
                    <label for="role-client">Client</label>
                </div>
                <div>
                    <input type="radio" name="role" id="role-specialist" value="SPECIALIST" v-model="this.form.role">
                    <label for="role-specialist">Specialist</label>
                </div>
            </div>
        </template>
        <template v-slot:submit-button>
            <FormSubmit value="Sign up" />
        </template>
        <template v-slot:links>
            <router-link :to="{ name: 'Login' }">
                Sign in
            </router-link>
        </template>
    </BaseAuthForm>
</template>

<script>
import BaseAuthForm from "./BaseAuthForm.vue";
import FormField from "./FormField.vue";
import FormSubmit from "./FormSubmit.vue";
import useVuelidate from "@vuelidate/core";
import { email, helpers, maxLength, minLength, required } from "@vuelidate/validators";
import { mapActions, mapState } from "pinia";
import { useUserStore } from "../../stores/userStore.js";
import { RegistrationDto } from "../../models/dto.js";

export default {
    name: "RegistrationForm",
    components: { FormSubmit, FormField, BaseAuthForm },
    setup() {
        return {
            v$: useVuelidate(),
        }
    },
    data() {
        return {
            form: {
                email: "",
                fullName: "",
                password: "",
                passwordRepeat: "",
                role: ""
            }
        }
    },
    computed: {
        ...mapState(useUserStore, {
            error: (state) => state.requestData.error
        })
    },
    validations() {
        return {
            form: {
                email: {
                    required: helpers.withMessage("This field is required", required),
                    email: helpers.withMessage("Not a valid email", email),
                },
                password: {
                    required: helpers.withMessage("This field is required", required),
                    minLength: helpers.withMessage("Min length is 5 characters", minLength(5)),
                    maxLength: helpers.withMessage("Max length is 255 characters", maxLength(255))
                },
                fullName: {
                    required: helpers.withMessage("This field is required", required),
                    minLength: helpers.withMessage("Min length is 5 characters", minLength(1)),
                    maxLength: helpers.withMessage("Max length is 255 characters", maxLength(255)),
                },
                passwordRepeat: {
                    sameAsPassword: helpers.withMessage("Must be the same as password", (v) => v === this.form.password)
                },
                role: {
                    required: helpers.withMessage("This field is required", required),
                }
            }
        }
    },
    methods: {
        ...mapActions(useUserStore, ['register']),
        async submit() {
            this.v$.form.$touch();
            if (this.v$.form.$error) {
                return;
            }

            let registrationDto = new RegistrationDto(
                this.form.email,
                this.form.fullName,
                this.form.password,
                this.form.passwordRepeat,
                this.form.role
            );

            await this.register(registrationDto);
            if (!this.error) {
                this.$router.push({ name: 'Home' });
            }
        }
    }
}

</script>
