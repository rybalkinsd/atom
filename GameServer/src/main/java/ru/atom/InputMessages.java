package ru.atom;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class InputMessages {
    private static ConcurrentHashMap<String, Vector<Message>> instance = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Vector<Message>> getInstance() {
        return instance;
    }
}
