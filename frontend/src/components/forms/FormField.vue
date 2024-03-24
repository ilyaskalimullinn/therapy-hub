<template>
    <div class="form-floating mb-3">
        <input :class="getClass" @input="this.$emit('update:modelValue', $event.target.value)" :name="name" :type="type"
            :id="id" :value="modelValue" :placeholder="placeholder" :required="required" />
        <label :for="id">{{ getMessage }}</label>
    </div>
</template>

<script>
export default {
    name: "FormField",
    emits: ['update:modelValue'],
    props: {
        name: {
            type: String,
            required: true
        },
        required: {
            type: Boolean,
            default: false
        },
        type: {
            type: String,
            default: "text"
        },
        id: {
            type: String,
            default: (props) => props.name
        },
        label: {
            type: String,
            default: (props) => props.name
        },
        placeholder: {
            type: String,
            default: (props) => props.label
        },
        modelValue: {
            type: String,
            default: "modelValue"
        },
        errors: {
            type: Array,
            default: () => [],
        }
    },
    computed: {
        getClass() {
            let res = "form-control ";
            if (this.errors[0]) {
                res += "is-invalid"
            }
            return res;
        },
        getMessage() {
            if (this.errors[0]) {
                return this.errors[0].$message;
            }
            return this.label;
        }
    }
}
</script>

<style scoped></style>
