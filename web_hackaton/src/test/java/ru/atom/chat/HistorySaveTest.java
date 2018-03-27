package ru.atom.chat;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class HistorySaveTest {

    String filePath = "../../../main/resources/history.txt";
    ChatMessage msg = new ChatMessage("blabla", new User("LolKek"));

    @Test
    public void historySave(){
        msg.saveInFile();
    }
}
