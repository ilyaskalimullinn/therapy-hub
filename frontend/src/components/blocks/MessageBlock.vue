<template>
    <div :class='`message ${this.getClass}`'>
        <div :class="`message-inner ${this.getClass}`">
            {{ message.body }}
        </div>
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
.message-inner {
    border: 1px solid black;
    padding: 10px;
    font-size: 15px;
    width: 60%;
}

.message .mine {
    text-align: right;
}

.message .other {
    text-align: left;
}

.message-inner.mine {
    background-color: lightgreen;
}

.message-inner.other {
    background-color: lightblue;
}
</style>
