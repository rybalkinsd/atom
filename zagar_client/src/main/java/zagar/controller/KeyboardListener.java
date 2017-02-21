package main.java.zagar.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import main.java.zagar.controller.handlers.*;
import main.java.zagar.network.packets.PacketMove;
import main.java.zagar.network.packets.PacketSplit;
import main.java.zagar.network.packets.PacketEjectMass;
import org.jetbrains.annotations.NotNull;
import main.java.zagar.Game;

public class KeyboardListener implements KeyListener {

  @NotNull
  static Map<Integer, Event> KeyEventMap= new HashMap<>();

  static{
    KeyEventMap.put(KeyEvent.VK_SPACE, new SplitEvent());
    KeyEventMap.put(KeyEvent.VK_W, new EjectMassEvent());
    KeyEventMap.put(KeyEvent.VK_T, new RapidEjectEvent());
    KeyEventMap.put(KeyEvent.VK_S, new MoveEvent());
  }
  @Override
  public void keyPressed(@NotNull KeyEvent e) {
      if (Game.socket != null && Game.socket.session != null) {
          if (Game.socket.session.isOpen()) {
              if (KeyEventMap.containsKey(e.getKeyCode()))
                KeyEventMap.get(e.getKeyCode()).handle();
          }
      }
  }

  @Override
  public void keyReleased(@NotNull KeyEvent e) {
    if (Game.socket != null && Game.socket.session != null) {
      if (Game.socket.session.isOpen()) {
        if (e.getKeyCode() == KeyEvent.VK_T) {
          Game.rapidEject = false;
        }
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}
