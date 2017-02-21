package zagar.view;

import org.jetbrains.annotations.NotNull;
import zagar.Game;
import zagar.Main;
import zagar.util.Colors;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

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

  private float rotationAngle = 0;
  private boolean rotating = true;

  private int staticVerges;

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

    if(isRotating()) {
      this.rotationAngle += (1f / (Math.max(this.mass, 20) * 2));
    }

    if (Game.cellNames.containsKey(this.id)) {
      this.name = Game.cellNames.get(this.id);
    }
  }

  public void render(@NotNull Graphics2D g, float scale) {
    if (Game.playerCells.size() > 0) {
      Color color = new Color(this.r, this.g, this.b);
      if (scale == 1) {
        color = new Color((int) (this.r / 1.3), (int) (this.g / 1.3), (int) (this.b / 1.3));
      }
      g.setColor(color);
      int size = (int) ((this.sizeRender * 2f * scale) * Game.zoom);

      float avgX = 0;
      float avgY = 0;

      for (Cell c : Game.playerCells) {
        if (c != null) {
          avgX += c.xRender;
          avgY += c.yRender;
        }
      }

      avgX /= Game.playerCells.size();
      avgY /= Game.playerCells.size();

      int x = (int) ((this.xRender - avgX) * Game.zoom) + GameFrame.size.width / 2 - size / 2;
      int y = (int) ((this.yRender - avgY) * Game.zoom) + GameFrame.size.height / 2 - size / 2;

      if (x < -size - 30 || x > GameFrame.size.width + 30 || y < -size - 30 || y > GameFrame.size.height + 30) {
        return;
      }

      int massRender = (int) ((this.size * this.size) / 100);
      if (virus) {
        Polygon hexagon = new Polygon();
        int verges = staticVerges != 0? staticVerges : 2 * (massRender / 8 + 10);
        verges = Math.min(verges, 100);
        for (int i = 0; i < verges; i++) {
          float pi = 3.14f;
          int spike = 0;
          if (i % 2 == 0) {
            spike = (int) (20 * Math.min(Math.max(1, (massRender / 80f)), 8) * Game.zoom);
          }
          hexagon.addPoint((int) (x + ((size + spike) / 2) * Math.cos(-rotationAngle + i * 2 * pi / verges)) + size / 2, (int) (y + ((size + spike) / 2) * Math.sin(-rotationAngle + i * 2 * pi / verges)) + size / 2);
        }
        g.fillPolygon(hexagon);
      } else {
        Polygon hexagon = new Polygon();
        int verges = staticVerges != 0? staticVerges : massRender / 20 + 5;
        verges = Math.min(verges, 50);
        for (int i = 0; i < verges; i++) {
          float pi = 3.14f;
          int pointX = (int) (x + (size / 2) * Math.cos(rotationAngle + i * 2 * pi / verges)) + size / 2;
          int pointY = (int) (y + (size / 2) * Math.sin(rotationAngle + i * 2 * pi / verges)) + size / 2;
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

  public void setColor(int r, int g, int b) {
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

  public void setColor(@NotNull Colors color){
    setColor(color.getR(),color.getG(),color.getB());
  }

  public float getRotationAngle() {
    return rotationAngle;
  }

  public void setRotationAngle(float rotationAngle) {
    this.rotationAngle = rotationAngle;
  }

  public boolean isRotating() {
    return rotating;
  }

  public void setRotating(boolean rotating) {
    this.rotating = rotating;
  }

  public void setStaticVerges(int staticVerges) {
    this.staticVerges = staticVerges;
  }

  public float getSize() {
    return size;
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