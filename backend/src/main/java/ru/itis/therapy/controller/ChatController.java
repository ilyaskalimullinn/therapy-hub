package ru.itis.therapy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.therapy.dto.request.SendMessageRequest;
import ru.itis.therapy.model.Chat;
import ru.itis.therapy.model.Message;
import ru.itis.therapy.repository.ChatRepository;
import ru.itis.therapy.repository.UserRepository;
import ru.itis.therapy.security.service.JWTService;

import java.util.Date;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @MessageMapping("/chat")
    @SendToUser("/queue/reply")
    public void processMessage(@Payload SendMessageRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long senderId = jwtService.getClaims(token.substring(7)).get("id").asLong();

        Chat chat;

        Optional<Chat> chatFromDb = chatRepository.findByFirstParticipantIdAndSecondParticipantId(senderId, request.getReceiverId());

        if (chatFromDb.isPresent()) {
            chat = chatFromDb.get();
        } else {
            chatFromDb = chatRepository.findByFirstParticipantIdAndSecondParticipantId(request.getReceiverId(), senderId);
            if (chatFromDb.isPresent()) {
                chat = chatFromDb.get();
            } else {
                chat = Chat.builder()
                        .firstParticipant(userRepository.findById(senderId).get())
                        .secondParticipant(userRepository.findById(request.getReceiverId()).get())
                        .build();

                chatRepository.save(chat);
            }
        }

        Message message = Message.builder()
                .chat(chat)
                .sender(userRepository.findById(senderId).get())
                .receiver(userRepository.findById(request.getReceiverId()).get())
                .body(request.getBody())
                .createdAt(new Date())
                .build();

        simpMessagingTemplate.convertAndSendToUser(
                request.getReceiverId().toString(),
                "/queue/reply",
                message
        );
    }
}
