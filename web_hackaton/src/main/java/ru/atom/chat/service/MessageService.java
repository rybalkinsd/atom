package ru.atom.chat.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import ru.atom.chat.dao.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void addMessage(String message) {
        message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss ")) + message;
        messageRepository.addMessage(message);
    }

    public String getAllMessages() {
        return messageRepository.getAllMessages();
    }
}
