package ru.atom.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteChatHistoryToFile {
    private static final Logger log = LogManager.getLogger(WriteChatHistoryToFile.class);
    private String path = "./Logs/History.txt";

    public WriteChatHistoryToFile(String text) {
        File file = new File(path);

        try (FileWriter fileWriter = new FileWriter(file, false)) {
            fileWriter.write("\n---------\n");
            fileWriter.write(text);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}