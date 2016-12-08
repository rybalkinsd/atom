package zagar.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import zagar.Game;
import zagar.controller.KeyboardListener;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
  @NotNull
  private static final Logger log = LogManager.getLogger(GameFrame.class);
  private static long startTime = System.currentTimeMillis();
  private static long frames = 0;
  private static final long serialVersionUID = 3637327282806739934L;
  @NotNull
  public GameCanvas canvas;
  public static double mouseX, mouseY;
  @NotNull
  private static Dimension minSize = new Dimension(800, 600);
  @NotNull
  private static Dimension size = minSize;

  public GameFrame() {
    setSize(minSize);
    setMinimumSize(minSize);
    addKeyListener(new KeyboardListener());
    canvas = new GameCanvas(minSize);
    getContentPane().add(canvas);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("· zAgar ·");
    //setCursor(getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
    pack();
    setVisible(true);
    addComponentListener(new SizeChangeListener());
  }

  public void render() {
    log.info("[RENDER]");
    log.info("CELLS:\n" + Arrays.toString(Game.cells));
    log.info("PLAYER CELLS SIZE: " + Game.player.size());
    log.info("LEADERBOARD:\n" + Arrays.toString(Game.leaderBoard));
    Point mouseP = getMouseLocation();
    mouseX = mouseP.getX();
    mouseY = mouseP.getY();
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
  public static Dimension getFrameSize() {
    return size;
  }

  @NotNull
  private Point getMouseLocation() {
    int x = (MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x);
    int y = (MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y - 24);
    return new Point(x, y);
  }

  private class SizeChangeListener implements ComponentListener {
    @Override
    public void componentResized(ComponentEvent componentEvent) {
      size=componentEvent.getComponent().getSize();
      log.trace("Changed GameFrame size to {}",size);
      canvas.setSize(size);
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
