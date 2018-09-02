package ru.atom.service;


import ru.atom.model.Player;

import java.util.concurrent.atomic.AtomicLong;

public class Connection {
    private long id;
    private Player player;
    private Thread invoker;
    private static AtomicLong idGenerator = new AtomicLong();

    public Connection(Thread invoker, Player player) {
        this.id = idGenerator.getAndIncrement();
        this.invoker = invoker;
        this.player = player;
    }

    public Thread getInvoker() {
        return this.invoker;
    }

    public long getId() {
        return this.id;
    }

    public Player getPlayer() {
        return this.player;
    }
}
