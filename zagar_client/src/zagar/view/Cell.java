package zagar.view;

import org.jetbrains.annotations.NotNull;
import zagar.Game;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Cell {
  public double x, y;
  public int id;
  public int size;
  @NotNull
  public final String name;
  public int mass;
  private final boolean virus;

  public Cell(double x, double y, float size, int id, boolean isVirus) {
    this.x = x;
    this.y = y;
    this.size = (int)size;
    this.id = id;
    this.virus = isVirus;
    this.name = "";
  }

  public Cell(double x, double y, float size, int id, boolean isVirus, String name) {
    this.x = x;
    this.y = y;
    this.size = (int)size;
    this.id = id;
    this.virus = isVirus;
    this.name = name;
  }

  public void render(@NotNull Graphics2D g) {
    if (Game.player.size() > 0) {
      float avgX = 0;
      float avgY = 0;
      for (Cell c : Game.player) {
        if (c != null) {
          avgX += c.x;
          avgY += c.y;
        }
      }
      avgX /= Game.player.size();
      avgY /= Game.player.size();
      int x = (int) ((this.x - avgX) * Game.zoom) + GameFrame.frame_size.width / 2;
      int y = (int) ((this.y - avgY) * Game.zoom) + GameFrame.frame_size.height / 2;
      if (virus) {
        Polygon hexagon = new Polygon();
          int sideNumber = 20;
        for (int i = 0; i < sideNumber; i++) {
          float pi = 3.14f;
          int spike = 0;
          if (i % 2 == 0) {
            spike = (int) (20 * Math.min(Math.max(1, (mass / 80f)), 8) * Game.zoom);
          }
          hexagon.addPoint((int) (x + ((size + spike) / 2) *
                          Math.cos(i * 2 * pi / sideNumber)) + size / 2,
                  (int) (y + ((size + spike) / 2) *
                          Math.sin(i * 2 * pi / sideNumber)) + size / 2);
        }
        g.setColor(new Color(150, 150, 0));
        g.fillPolygon(hexagon);
      } else {
        Polygon hexagon = new Polygon();
        int sideNumber = 12;
        for (int i = 0; i < sideNumber; i++) {
          float pi = 3.14f;
          int pointX = (int) (x + (size / 2) * Math.cos(i * 2 * pi / sideNumber)) + size / 2;
          int pointY = (int) (y + (size / 2) * Math.sin(i * 2 * pi / sideNumber)) + size / 2;
          hexagon.addPoint(pointX, pointY);
        }
        g.setColor(new Color(0, 150, 0));
        g.fillPolygon(hexagon);
      }
      if (this.name.length() > 0 || (this.mass > 30 && !this.virus)) {
        Font font = new Font("Ubuntu", Font.BOLD, size / name.length());
        g.setFont(font);
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        FontMetrics fm = img.getGraphics().getFontMetrics(font);
        int fontSize = fm.stringWidth(this.name);
        outlineString(g, this.name, x + size / 2 - fontSize / 2, y + size / 2);
      }
    }
  }

  private void outlineString(Graphics2D g, String string, int x, int y) {
    g.setColor(new Color(70, 70, 70));
    g.drawString(string, x - 1, y);
    g.drawString(string, x + 1, y);
    g.drawString(string, x, y - 1);
    g.drawString(string, x, y + 1);
    g.setColor(new Color(255, 255, 255));
    g.drawString(string, x, y);
  }

  @Override
  public String toString() {
    return "Cell{" +
        "x=" + x +
        ", y=" + y +
        ", id=" + id +
        ", size=" + size +
        '}';
  }
}