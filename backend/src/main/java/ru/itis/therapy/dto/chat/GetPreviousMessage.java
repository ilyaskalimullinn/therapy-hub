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
public class GetPreviousMessage extends MetaMessage {
    private MessageData data;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class MessageData {
        private Long chatId;
    }
}
