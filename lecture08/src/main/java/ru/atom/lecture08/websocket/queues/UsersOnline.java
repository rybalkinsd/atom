package ru.atom.lecture08.websocket.queues;

import org.springframework.stereotype.Repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class UsersOnline {
    static private Queue<String> usersOnline =  new ConcurrentLinkedQueue<>();

    public static Queue<String> getUsersOnline() {
        return usersOnline;
    }
}
