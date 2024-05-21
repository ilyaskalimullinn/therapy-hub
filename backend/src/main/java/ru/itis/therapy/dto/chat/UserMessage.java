package ru.itis.therapy.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class UserMessage extends MetaMessage {
    private UserMessageData data;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class UserMessageData {
        private Long senderId;
        private Long receiverId;
        private Long chatId;
        private String body;

        public String getFullReceiverId() {
            return this.chatId + "-" + this.receiverId;
        }

        public String getFullSenderId() {
            return this.chatId + "-" + this.senderId;
        }
    }
}
