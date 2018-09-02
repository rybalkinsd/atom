package ru.atom.service;

import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.atomic.AtomicLong;


public class Player {

    private long id;
    private String name;

    public Player(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return this.id;
    }

}
