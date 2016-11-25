package zagar.view;

import org.jetbrains.annotations.NotNull;
import zagar.controller.KeyboardListener;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
  private static long startTime = System.currentTimeMillis();
  private static long frames = 0;
  private static final long serialVersionUID = 3637327282806739934L;
  @NotNull
  public GameCanvas canvas;
  public static double mouseX, mouseY;
  @NotNull
  public static Dimension size = new Dimension(1100, 700);

  public GameFrame() {
    setSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setPreferredSize(size);
    addKeyListener(new KeyboardListener());
    canvas = new GameCanvas();
    getContentPane().add(canvas);
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("· zAgar ·");
    //setCursor(getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
    pack();
    setVisible(true);
  }

  public void render() {
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
  private Point getMouseLocation() {
    int x = (MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x);
    int y = (MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y - 24);
    return new Point(x, y);
  }
}
