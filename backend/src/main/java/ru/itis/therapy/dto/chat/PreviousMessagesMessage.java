package ru.itis.therapy.dto.chat;

import java.util.List;

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
public class PreviousMessagesMessage extends MetaMessage {
    private MessageData data;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class MessageData {
        private List<UserMessageData> messages;
    }
}
