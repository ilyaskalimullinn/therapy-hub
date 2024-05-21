<template>
    <input type="submit" :value="value" class="submit-button">
</template>

<script>
export default {
    name: "FormSubmit",
    props: ["value"]
}
</script>

<style scoped>
.submit-button {
    padding: 10px;
    border-radius: 10px;
    font-size: 20px;
    background-color: lightgreen;
}

.submit-button:active, .submit-button:hover {
    transition: .2s;
    opacity: 0.8;
}
</style>
