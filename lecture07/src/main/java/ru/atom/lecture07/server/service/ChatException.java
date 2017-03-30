package ru.atom.lecture07.server.service;

/**
 * @author apomosov
 * @since 05.04.17
 */
public class ChatException extends Exception {
    public ChatException(String login) {
        super(login);
    }
}
