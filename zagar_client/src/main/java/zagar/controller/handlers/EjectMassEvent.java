package main.java.zagar.controller.handlers;

import main.java.zagar.network.packets.PacketEjectMass;

import java.io.IOException;

/**
 * Created by Klissan on 29.11.2016.
 */
public class EjectMassEvent implements  Event  {
    @Override
    public void handle() {
        try {
            new PacketEjectMass().write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
