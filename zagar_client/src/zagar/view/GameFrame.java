package zagar.view;

import org.jetbrains.annotations.NotNull;
import zagar.controller.KeyboardListener;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
  @NotNull
  public GameCanvas canvas = new GameCanvas();
  public static double mouseX, mouseY;
  public static Dimension frame_size = new Dimension(1200, 700);

  public GameFrame() {
    setSize(frame_size);
    setMinimumSize(frame_size);
    setMaximumSize(frame_size);
    setPreferredSize(frame_size);
    addKeyListener(new KeyboardListener());
    getContentPane().add(canvas);
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("· zAgar ·");
    pack();
    setVisible(true);
  }

  public void render() {
    Point mouseP = getMouseLocation();
    mouseX = mouseP.getX();
    mouseY = mouseP.getY();
    canvas.render();
  }

  @NotNull
  private Point getMouseLocation() {
    int x = (MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x);
    int y = (MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y - 24);
    return new Point(x, y);
  }
}