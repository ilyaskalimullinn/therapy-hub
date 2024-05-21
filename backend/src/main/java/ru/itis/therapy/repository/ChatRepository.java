package ru.itis.therapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.therapy.model.Chat;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByFirstParticipantIdAndSecondParticipantId(Long firstParticipantId, Long secondParticipantId);

    @Query("select c from Chat c where c.firstParticipant.id = :id or c.secondParticipant.id = :id")
    List<Chat> findAllWhereFirstParticipantIdEqualsOrSecondParticipantIdEquals(@Param("id") Long id);
} 
