package ru.atom.lecture09.serialization;

import java.io.Serializable;

public class Packet implements Serializable {
    private static final long serialVersionUID = 123123123123L;

    private String payload;

    //TODO Send your name here
    public Packet(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "payload='" + payload + '\'' +
                '}';
    }
}
