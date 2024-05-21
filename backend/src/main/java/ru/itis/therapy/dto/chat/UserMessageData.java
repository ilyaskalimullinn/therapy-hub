package ru.itis.therapy.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserMessageData {
    private Long id;
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
