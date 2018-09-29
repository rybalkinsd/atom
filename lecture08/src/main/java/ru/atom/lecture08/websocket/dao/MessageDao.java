package ru.atom.lecture08.websocket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.atom.lecture08.websocket.model.Message;
import ru.atom.lecture08.websocket.model.User;

import java.util.List;

@Repository
public interface MessageDao extends CrudRepository<Message, Integer> {
    List<Message> getAllByUser(User user);
}