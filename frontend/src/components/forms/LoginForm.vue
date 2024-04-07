<template>
    <BaseAuthForm @submit.prevent="this.submit" :error="this.error" title="Sign in">
        <template v-slot:fields>
            <FormField name="email" v-model="this.form.email" :errors="v$.form.email.$errors" label="Email" type="email"
                required />
            <FormField name="password" v-model="this.form.password" :errors="v$.form.password.$errors" label="Password"
                type="password" required />
        </template>
        <template v-slot:submit-button>
            <FormSubmit value="Sign in" />
        </template>
        <template v-slot:links>
            <router-link :to="{ name: 'Registration' }">
                Sign up
            </router-link>
        </template>
    </BaseAuthForm>
</template>

<script>
import BaseAuthForm from "./BaseAuthForm.vue";
import FormField from "./FormField.vue";
import useVuelidate from "@vuelidate/core";
import { email, maxLength, minLength, required, helpers } from "@vuelidate/validators";
import { useUserStore } from "../../stores/userStore.js";
import { mapActions, mapState } from "pinia";
import FormSubmit from "./FormSubmit.vue";


export default {
    name: "LoginForm",
    components: { FormSubmit, FormField, BaseAuthForm },
    setup() {
        return {
            v$: useVuelidate()
        }
    },
    data() {
        return {
            form: {
                email: "",
                password: ""
            }
        }
    },
    computed: {
        ...mapState(useUserStore, {
            error: (state) => state.requestData.error
        })
    },
    validations: {
        form: {
            email: {
                required: helpers.withMessage("This field is required", required),
                email: helpers.withMessage("Not a valid email", email),
            },
            password: {
                required: helpers.withMessage("This field is required", required),
                minLength: helpers.withMessage("Min length is 5 characters", minLength(5)),
                maxLength: helpers.withMessage("Max length is 255 characters", maxLength(255))
            }
        }
    },
    methods: {
        ...mapActions(useUserStore, ['login']),
        async submit() {
            this.v$.form.$touch();
            if (this.v$.form.$error) {
                return;
            }
            await this.login(this.form.email, this.form.password);
            if (!this.error) {
                this.$router.push({ name: 'Home' });
            }
        }
    }
}
</script>

<style scoped></style>
