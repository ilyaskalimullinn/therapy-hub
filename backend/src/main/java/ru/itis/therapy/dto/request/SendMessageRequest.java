package ru.itis.therapy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
    private String messageType;
    private Long chatId;
    private Long senderId;
    private Long receiverId;
    private String body;

    // public enum MessageType {
    //     userMessage
    // }

    public String getFullReceiverId() {
        return this.chatId + "-" + this.receiverId;
    }
}
