package zagar.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import zagar.network.packets.PacketSplit;
import zagar.network.packets.PacketEjectMass;
import org.jetbrains.annotations.NotNull;
import zagar.Game;

public class KeyboardListener implements KeyListener {
  @Override
  public void keyPressed(@NotNull KeyEvent e) {
    try {
      if (Game.socket != null && Game.socket.session != null) {
        if (Game.socket.session.isOpen()) {
          if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            new PacketSplit().write();
          }
          if (e.getKeyCode() == KeyEvent.VK_W) {
            new PacketEjectMass().write();
          }
          if (e.getKeyCode() == KeyEvent.VK_T) {
            Game.rapidEject = true;
          }
          if (e.getKeyCode() == KeyEvent.VK_R) {
            if (Game.player.size() == 0) {
              Game.respawn();
            }
          }
        }
      }
    } catch (IOException ioEx) {
      ioEx.printStackTrace();
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
