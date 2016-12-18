package main.java.zagar.view;

import main.java.zagar.Game;
import main.java.zagar.controller.KeyboardListener;
import main.java.zagar.network.packets.PacketWindowSize;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.util.Arrays;

public class GameFrame extends JFrame {
  @NotNull
  private static final Logger log = LogManager.getLogger(GameFrame.class);
  private static final long serialVersionUID = 3637327282806739934L;
  @NotNull
  private static Dimension minSize = new Dimension(800, 600);
  private static long startTime = System.currentTimeMillis();
  private static long frames = 0;
  @NotNull
  private GameCanvas canvas;

  public GameFrame() {
    setSize(minSize);
    setMinimumSize(minSize);
    addKeyListener(new KeyboardListener());
    canvas = new GameCanvas(getSize());
    getContentPane().add(canvas);
    addComponentListener(new SizeChangeListener());
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("· zAgar ·");
    pack();
    setVisible(true);
  }

  public void render() {
    log.info("[RENDER]");
      log.info("CELLS:\n" + Game.getCells().toString());
      log.info("PLAYER CELLS SIZE: " + Game.getPlayers().size());
    log.info("LEADERBOARD:\n" + Arrays.toString(Game.leaderBoard));
    frames++;
    if (System.currentTimeMillis() - startTime > 1000) {
      if (frames < 10) {
        System.err.println("LAG > There were only " + frames + " frames in " + (System.currentTimeMillis() - startTime) + "ms!!!");
      }
      frames = 0;
      startTime = System.currentTimeMillis();
    }
    canvas.render();
  }

  @NotNull
  public GameCanvas getCanvas() {
    return canvas;
  }

  private class SizeChangeListener implements ComponentListener {
    @Override
    public void componentResized(ComponentEvent componentEvent) {
      //do nothing if not authorized
      if (Game.state != Game.GameState.AUTHORIZED) return;
      Dimension size = componentEvent.getComponent().getSize();
      canvas.setSize(size);
      try {
        new PacketWindowSize(size).write();
      } catch (IOException e) {
        log.warn("Cannot send window size, got exception {}", e);
      }
    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {

    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }
  }
}
