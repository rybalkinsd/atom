package main.java.zagar.controller.handlers;

import main.java.zagar.network.packets.PacketSplit;

import java.io.IOException;

/**
 * Created by Klissan on 29.11.2016.
 */
public class SplitEvent implements  Event {
    @Override
    public void handle() {
        try {
            new PacketSplit().write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
