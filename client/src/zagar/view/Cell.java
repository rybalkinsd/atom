package zagar.view;

import org.jetbrains.annotations.NotNull;
import zagar.Game;
import zagar.Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;

public class Cell {
  public double x, y;
  public int id;
  public float size;
  private int r, g, b;
  @NotNull
  public String name = "";
  public float sizeRender;
  public double xRender;
  public double yRender;
  public int mass;
  private final boolean virus;
  private float rotation = 0;

  public Cell(double x, double y, float size, int id, boolean isVirus) {
    this.x = x;
    this.y = y;
    this.size = size;
    this.id = id;
    this.virus = isVirus;
    this.xRender = this.x;
    this.yRender = this.y;
    this.sizeRender = this.size;
  }

  public void tick() {
    this.xRender -= (this.xRender - x) / 5f;
    this.yRender -= (this.yRender - y) / 5f;
    this.sizeRender -= (this.sizeRender - size) / 9f;
    this.mass = Math.round((this.sizeRender * this.sizeRender) / 100);
    this.rotation += (1f / (Math.max(this.mass, 20) * 2));

    if (Game.cellNames.containsKey(this.id)) {
      this.name = Game.cellNames.get(this.id);
    }
  }

  public void render(@NotNull Graphics2D g, float scale) {
    if (Game.player.size() > 0) {
      Color color = new Color(this.r, this.g, this.b);
      if (scale == 1) {
        color = new Color((int) (this.r / 1.3), (int) (this.g / 1.3), (int) (this.b / 1.3));
      }
      g.setColor(color);
      int size = (int) ((this.sizeRender * 2f * scale) * Game.zoom);

      float avgX = 0;
      float avgY = 0;

      for (Cell c : Game.player) {
        if (c != null) {
          avgX += c.xRender;
          avgY += c.yRender;
        }
      }

      avgX /= Game.player.size();
      avgY /= Game.player.size();

      int x = (int) ((this.xRender - avgX) * Game.zoom) + GameFrame.size.width / 2 - size / 2;
      int y = (int) ((this.yRender - avgY) * Game.zoom) + GameFrame.size.height / 2 - size / 2;

      if (x < -size - 30 || x > GameFrame.size.width + 30 || y < -size - 30 || y > GameFrame.size.height + 30) {
        return;
      }

      int massRender = (int) ((this.size * this.size) / 100);
      if (virus) {
        Polygon hexagon = new Polygon();
        int a = 2 * (massRender / 8 + 10);
        a = Math.min(a, 100);
        for (int i = 0; i < a; i++) {
          float pi = 3.14f;
          int spike = 0;
          if (i % 2 == 0) {
            spike = (int) (20 * Math.min(Math.max(1, (massRender / 80f)), 8) * Game.zoom);
          }
          hexagon.addPoint((int) (x + ((size + spike) / 2) * Math.cos(-rotation + i * 2 * pi / a)) + size / 2, (int) (y + ((size + spike) / 2) * Math.sin(-rotation + i * 2 * pi / a)) + size / 2);
        }
        g.fillPolygon(hexagon);
      } else {
        Polygon hexagon = new Polygon();
        int a = massRender / 20 + 5;
        a = Math.min(a, 50);
        for (int i = 0; i < a; i++) {
          float pi = 3.14f;
          int pointX = (int) (x + (size / 2) * Math.cos(rotation + i * 2 * pi / a)) + size / 2;
          int pointY = (int) (y + (size / 2) * Math.sin(rotation + i * 2 * pi / a)) + size / 2;
          hexagon.addPoint(pointX, pointY);
        }
        g.fillPolygon(hexagon);
      }

      if (this.name.length() > 0 || (this.mass > 30 && !this.virus)) {
        Font font = Main.frame.canvas.fontCells;
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        FontMetrics fm = img.getGraphics().getFontMetrics(font);

        int fontSize = fm.stringWidth(this.name);

        outlineString(g, this.name, x + size / 2 - fontSize / 2, y + size / 2);

        String mass = this.mass + "";

        int massSize = fm.stringWidth(mass);

        outlineString(g, mass, x + size / 2 - massSize / 2, y + size / 2 + 17);
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

  public void setColor(byte r, byte g, byte b) {
    this.r = r;
    this.g = g;
    this.b = b;
    if (r < 0) {
      this.r = r + 256;
    }
    if (g < 0) {
      this.g = g + 256;
    }
    if (b < 0) {
      this.b = b + 256;
    }
  }
}