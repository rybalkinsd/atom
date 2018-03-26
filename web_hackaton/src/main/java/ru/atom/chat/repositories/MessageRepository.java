package ru.atom.chat.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.atom.chat.models.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAll();
}
