package ru.itis.therapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.therapy.model.Chat;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByFirstParticipantIdAndSecondParticipantId(Long firstParticipantId, Long secondParticipantId);
}
