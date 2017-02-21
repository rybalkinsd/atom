package main.java.zagar.controller.handlers;

import main.java.zagar.Game;
import main.java.zagar.network.packets.PacketMove;

import java.io.IOException;

/**
 * Created by Klissan on 29.11.2016.
 */
public class MoveEvent implements  Event  {
    @Override
    public void handle() {
        try {
            new PacketMove(1,1).write(Game.socket.session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
