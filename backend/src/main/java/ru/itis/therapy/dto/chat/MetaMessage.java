package ru.itis.therapy.dto.chat;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(name = "UserMessage", value = UserMessage.class),
        @JsonSubTypes.Type(name = "GetPreviousMessage", value = GetPreviousMessage.class),
        @JsonSubTypes.Type(name = "PreviousMessagesMessage", value = PreviousMessagesMessage.class)
})
public abstract class MetaMessage {
    public abstract Object getData();
}
