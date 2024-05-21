package ru.itis.therapy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.therapy.dto.request.CreateAppointmentRequest;
import ru.itis.therapy.dto.request.CreateChatRequest;
import ru.itis.therapy.dto.response.AppointmentResponse;
import ru.itis.therapy.dto.response.ChatResponse;
import ru.itis.therapy.dto.response.CreateChatResponse;
import ru.itis.therapy.dto.response.ChatResponse.ChatResponseBuilder;
import ru.itis.therapy.dto.response.ChatResponse.ParticipantInfo;
import ru.itis.therapy.exception.CantCreateChatException;
import ru.itis.therapy.model.Chat;
import ru.itis.therapy.model.User;
import ru.itis.therapy.model.User.UserRole;
import ru.itis.therapy.repository.ChatRepository;
import ru.itis.therapy.repository.UserRepository;
import ru.itis.therapy.security.service.JWTService;
import ru.itis.therapy.service.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("${api.uri}/chat")
@RequiredArgsConstructor
public class ChatController {
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @PostMapping("/new")
    public ResponseEntity<CreateChatResponse> create(@RequestBody CreateChatRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        User currUser = userRepository.findById(jwtService.getClaims(token.substring(7)).get("id").asLong()).get();
        if (!currUser.getRole().equals(UserRole.CLIENT)) {
            throw new CantCreateChatException();
        }

        User participant = userRepository.findById(request.getParticipantId()).get();
        if (!participant.getRole().equals(UserRole.SPECIALIST)) {
            throw new CantCreateChatException();
        }

        Chat chat = Chat.builder()
            .firstParticipant(currUser)
            .secondParticipant(participant)
            .build();
        Chat createdChat = chatRepository.save(chat);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateChatResponse.builder()
                                                            .chatId(createdChat.getId())
                                                            .build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatResponse>> getAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<ChatResponse> chats = chatRepository.findAllWhereFirstParticipantIdEqualsOrSecondParticipantIdEquals(jwtService.getClaims(token.substring(7)).get("id").asLong())
        .stream().map(c -> {
            ChatResponse response = new ChatResponse();
            response.setId(c.getId());
            User participant;
            if (c.getFirstParticipant().getId().equals(jwtService.getClaims(token.substring(7)).get("id").asLong())) {
                participant = c.getSecondParticipant();
            } else {
                participant = c.getFirstParticipant();
            }
            response.setParticipant(ParticipantInfo.builder()
                                    .id(participant.getId())
                                    .fullName(participant.getFullName())
                                    .build());
            return response;
        }).toList();

        return ResponseEntity.status(HttpStatus.OK).body(chats);
    }
}
