package ru.itis.therapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.therapy.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatIdOrderByCreatedAtAsc(Long chatId);
}
