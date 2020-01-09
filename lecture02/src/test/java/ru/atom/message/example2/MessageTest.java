package ru.atom.message.example2;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class MessageTest {
    @Test
    public void saveTo() throws Exception {
        Storable smthToSave = new Message("Perfect content");
        smthToSave.saveTo(new File("path to file"));

        assertTrue(smthToSave instanceof Message); // <-- OK
        assertTrue(smthToSave instanceof Storable); // <-- OK
    }

}