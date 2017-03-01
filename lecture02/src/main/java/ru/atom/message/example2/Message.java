package ru.atom.message.example2;

import java.io.File;

/**
 * Created by sergey on 2/28/17.
 */
public class Message implements Storable {
    private String content;

    public Message(String content) {
        this.content = content;
    }

    @Override
    public void saveTo(File file) {
        // some stuff to save Message to file
        System.out.println("Save to in action");
    }

    // ...
}
