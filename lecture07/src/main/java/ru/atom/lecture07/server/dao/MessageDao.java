package ru.atom.lecture07.server.dao;

import org.springframework.data.repository.CrudRepository;
import ru.atom.lecture07.server.model.Message;

public interface MessageDao extends CrudRepository<Message, Integer> {
}
