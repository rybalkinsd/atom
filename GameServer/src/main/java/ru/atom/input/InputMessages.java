package ru.atom.input;

import ru.atom.message.Message;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class InputMessages {
    private static ConcurrentHashMap<String, Vector<Message>> instance = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Vector<Message>> getInstance() {
        return instance;
    }

    public static void addEntry(String name) {
        instance.putIfAbsent(name, new Vector<Message>());
    }




}
