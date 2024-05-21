<template>
    <div :class='`message ${this.getClass}`' >
        {{ message.body }}
    </div>
</template>

<script>
import { useStompStore } from '@/stores/stompStore';
import { useUserStore } from '@/stores/userStore';
import { mapState } from 'pinia';


export default {
    name: "MessageBlock",
    props: ["message"],
    computed: {
        ...mapState(useUserStore, ["user"]),
        ...mapState(useStompStore, ["participant"]),
        getClass() {
            if (this.message.senderId === this.user.id) {
                return "mine";
            }
            // console.log(this.message.senderId, this.user.id)
            return "other"
        },
    }
}
</script>

<style scoped>
.message {
    border: 1px solid black;
}

.mine {
    background-color: lightgreen;
}

.other {
    background-color: lightblue;
}
</style>
