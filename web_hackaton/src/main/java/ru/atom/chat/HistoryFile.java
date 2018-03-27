package ru.atom.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class HistoryFile {
    private static final Logger log = LoggerFactory.getLogger(HistoryFile.class);

    private File historyFile;
    private FileWriter writer;

    @Bean
    public HistoryFile getNewHistroryFile () {
        return new HistoryFile();
    }

    HistoryFile() {
        historyFile = new File("src/main/resources/history.txt");
        try {
            writer = new FileWriter(historyFile,true);
        } catch (FileNotFoundException e) {
            log.warn("historyFile not found.");
        } catch (IOException e) {
            log.warn("IOException.");
        }
    }

    public File getHistoryFile() {
        return historyFile;
    }

    public void write (String line) {
        try {
            writer.write(line);
            writer.flush();
        } catch (FileNotFoundException e) {
            log.warn("historyFile not found.");
        } catch (NullPointerException e) {
            log.warn("NullPointerException");
        } catch (IOException e) {
            log.warn("IOException.");
        }
    }
}
