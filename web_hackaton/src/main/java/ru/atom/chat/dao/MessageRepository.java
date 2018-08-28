package ru.atom.chat.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {
    @Value("${historyFile}")
    private String historyFilePath;
    private Queue<String> messages = new ConcurrentLinkedQueue<>();

    @PostConstruct
    public void loadHistory() throws IOException {
        Path historyFile = Paths.get(historyFilePath);
        BufferedReader reader = Files.newBufferedReader(historyFile);
        reader.lines().forEach(messages::add);
    }

    public void saveMessage(String message) {
        try {
            Path historyFile = Paths.get(historyFilePath);
            BufferedWriter writer = Files.newBufferedWriter(historyFile, StandardOpenOption.APPEND);
            writer.append(message).append('\n').flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(String message) {
        messages.add(message);
        saveMessage(message);
    }

    public String getAllMessages() {
        return messages.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
