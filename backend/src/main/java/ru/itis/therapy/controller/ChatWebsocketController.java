package ru.itis.therapy.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import ru.itis.therapy.dto.chat.GetPreviousMessage;
import ru.itis.therapy.dto.chat.MetaMessage;
import ru.itis.therapy.dto.chat.PreviousMessagesMessage;
import ru.itis.therapy.dto.chat.UserMessage;
import ru.itis.therapy.dto.chat.UserMessageData;
import ru.itis.therapy.dto.request.SendMessageRequest;
import ru.itis.therapy.model.Chat;
import ru.itis.therapy.model.Message;
import ru.itis.therapy.model.User;
import ru.itis.therapy.repository.ChatRepository;
import ru.itis.therapy.repository.MessageRepository;
import ru.itis.therapy.repository.UserRepository;
import ru.itis.therapy.security.service.JWTService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatWebsocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @MessageMapping("/chat")
    public void processMessage(@Payload MetaMessage metaMessage,
            SimpMessageHeaderAccessor simpMessageHeaderAccessor) {

        if (metaMessage instanceof UserMessage) {
            handleUserMessage((UserMessage) metaMessage, simpMessageHeaderAccessor);
        } else if (metaMessage instanceof GetPreviousMessage) {
            handleGetPreviousMessage((GetPreviousMessage) metaMessage, simpMessageHeaderAccessor);
        } else {
            throw new IllegalArgumentException("No such message type");
        }
    }

    private void handleGetPreviousMessage(GetPreviousMessage metaMessage,
            SimpMessageHeaderAccessor simpMessageHeaderAccessor) {

        List<Message> messages = this.messageRepository.findAllByChatId(metaMessage.getData().getChatId());
        Chat chat = (Chat) simpMessageHeaderAccessor.getSessionAttributes().get("chat");

        List<UserMessageData> userMessageDataList = messages
                .stream()
                .map(m -> UserMessageData.builder()
                                .id(m.getId())
                                .body(m.getBody())
                                .chatId(chat.getId())
                                .receiverId(m.getReceiver().getId())
                                .senderId(m.getSender().getId())
                                .build()
                    )
                .toList();

        PreviousMessagesMessage previousMessagesMessage = PreviousMessagesMessage.builder()
                .data(PreviousMessagesMessage.MessageData.builder()
                        .messages(userMessageDataList)
                        .build())
                .build();
        
        String userFullId = (String) simpMessageHeaderAccessor.getSessionAttributes().get("userFullId");

        simpMessagingTemplate.convertAndSendToUser(
                userFullId, "/queue/messages",
                previousMessagesMessage);
    }

    protected void handleUserMessage(UserMessage metaMessage, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        UserMessageData data = metaMessage.getData();
        User sender = (User) simpMessageHeaderAccessor.getSessionAttributes().get("sender");
        Chat chat = (Chat) simpMessageHeaderAccessor.getSessionAttributes().get("chat");
        User receiver = this.userRepository.findById(data.getReceiverId()).get();

        Message message = Message.builder()
                .chat(chat)
                .sender(sender)
                .receiver(receiver)
                .body(data.getBody())
                .createdAt(new Date())
                .build();

        message = messageRepository.save(message);
        metaMessage.getData().setSenderId(sender.getId());
        metaMessage.getData().setId(message.getId());

        simpMessagingTemplate.convertAndSendToUser(
                data.getFullReceiverId(), "/queue/messages",
                metaMessage);

        simpMessagingTemplate.convertAndSendToUser(
                data.getFullSenderId(), "/queue/messages",
                metaMessage);
    }

    @EventListener
    public void handleConnectEvent(SessionConnectEvent event) {
        System.out.println("Subscribed");
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String token = headers.getNativeHeader("Authorization").get(0);
        Long userId = jwtService.getClaims(token.substring(7)).get("id").asLong();
        User user = userRepository.findById(userId).get();

        headers.getSessionAttributes().put("sender", user);
    }

    @EventListener
    public void handleSubscriptionEvent(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Long chatId = this.getChatIdFromDestination(headers.getDestination());

        Long userId = ((User) headers.getSessionAttributes().get("sender")).getId();

        Chat chat = this.chatRepository.findById(chatId).get();

        if ((chat.getFirstParticipant().getId() != userId) && (chat.getSecondParticipant().getId() != userId)) {
            throw new IllegalArgumentException("This chat can't be accessed by this user");
        }

        String userFullId = chat.getId() + "-" + userId;

        headers.getSessionAttributes().put("chat", chat);
        headers.getSessionAttributes().put("userFullId", userFullId);
    }

    private Long getChatIdFromDestination(String destination) {
        return Long.parseLong(destination.split("/")[2].split("-")[0]);
    }
}

// @MessageMapping("/chat")
// @SendToUser("/queue/reply")
// public void processMessage(@Payload SendMessageRequest request,
// @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
// Long senderId = jwtService.getClaims(token.substring(7)).get("id").asLong();

// Chat chat;

// Optional<Chat> chatFromDb =
// chatRepository.findByFirstParticipantIdAndSecondParticipantId(senderId,
// request.getReceiverId());

// if (chatFromDb.isPresent()) {
// chat = chatFromDb.get();
// } else {
// chatFromDb =
// chatRepository.findByFirstParticipantIdAndSecondParticipantId(request.getReceiverId(),
// senderId);
// if (chatFromDb.isPresent()) {
// chat = chatFromDb.get();
// } else {
// chat = Chat.builder()
// .firstParticipant(userRepository.findById(senderId).get())
// .secondParticipant(userRepository.findById(request.getReceiverId()).get())
// .build();

// chatRepository.save(chat);
// }
// }

// Message message = Message.builder()
// .chat(chat)
// .sender(userRepository.findById(senderId).get())
// .receiver(userRepository.findById(request.getReceiverId()).get())
// .body(request.getBody())
// .createdAt(new Date())
// .build();

// simpMessagingTemplate.convertAndSendToUser(
// request.getReceiverId().toString(),
// "/queue/reply",
// message
// );
// }
