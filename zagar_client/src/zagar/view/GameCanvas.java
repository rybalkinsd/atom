package zagar.view;

import org.jetbrains.annotations.NotNull;
import zagar.Game;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;

import javax.swing.JPanel;

public class GameCanvas extends JPanel {
  private static final long serialVersionUID = 5570080027060608254L;
  private BufferedImage screen;
  private Font font = new Font("Ubuntu", Font.BOLD, 30);
  private Font fontLB = new Font("Ubuntu", Font.BOLD, 25);
  public Font fontCells = new Font("Ubuntu", Font.BOLD, 18);

  public GameCanvas(@NotNull Dimension startSize) {
    screen = new BufferedImage(startSize.width, startSize.height, BufferedImage.TYPE_INT_ARGB);
    setFont(font);
    setSize(startSize);
    addComponentListener(new SizeChangeListener());
    setVisible(true);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    render();
  }

  public void render() {
    Graphics ggg = screen.getGraphics();
    Graphics2D g = ((Graphics2D) ggg);
    g.setColor(new Color(255, 255, 255));
    g.fillRect(0, 0, getParent().getWidth(), getParent().getHeight());

    g.setColor(new Color(220, 220, 220));

    if (Game.fps < 30) {
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    } else {
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    if (Game.player.size() > 0) {
      int size = 1;

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

      g.setStroke(new BasicStroke(2));

      for (double i = avgX - (getParent().getWidth() / 2) / Game.zoom; i < avgX + (getParent().getWidth() / 2) / Game.zoom; i += 100) {
        i = (int) (i / 100) * 100;
        int x = (int) ((i - avgX) * Game.zoom) + getParent().getWidth() / 2 - size / 2;
        g.drawLine((int) x, (int) Game.minSizeY, (int) x, (int) Game.maxSizeY);
      }
      for (double i = avgY - (getParent().getHeight() / 2) / Game.zoom; i < avgY + (getParent().getHeight() / 2) / Game.zoom; i += 100) {
        i = (int) (i / 100) * 100;
        int y = (int) ((i - avgY) * Game.zoom) + getParent().getHeight() / 2 - size / 2;
        g.drawLine((int) Game.minSizeX, (int) y, (int) Game.maxSizeX, (int) y);
      }
    }

    g.setFont(fontCells);

    for (int i2 = 0; i2 < Game.cells.length; i2++) {
      Cell cell = Game.cells[i2];
      if (cell != null) {
        cell.render(g, 1);
        if (cell.mass > 9) {
          cell.render(g, Math.max(1 - 1f / (cell.mass / 10f), 0.87f));
        }
      }
    }

    g.setFont(font);

    String scoreString = "Score: " + Game.score;

    g.setColor(new Color(0, 0, 0, 0.5f));

    g.fillRect(getParent().getWidth() - 202, 10, 184, 265);
    g.fillRect(7, getParent().getHeight() - 85, getStringWidth(g, scoreString) + 26, 47);

    g.setColor(Color.WHITE);

    g.drawString(scoreString, 20, getParent().getHeight() - 50);

    int i = 0;

    g.setFont(fontLB);

    g.drawString("Leaderboard", getParent().getWidth() - 110 - getStringWidth(g, "Leaderboard") / 2, 40);

    g.setFont(fontCells);

    for (String s : Game.leaderBoard) {
      if (s != null) {
        g.drawString(s, getParent().getWidth() - 110 - getStringWidth(g, s) / 2, 40 + 22 * (i + 1));
      }
      i++;
    }

    g.dispose();

    Graphics gg = this.getGraphics();
    gg.drawImage(screen, 0, 0, null);
    gg.dispose();
  }

  private int getStringWidth(Graphics2D g, String string) {
    BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    FontMetrics fm = img.getGraphics().getFontMetrics(g.getFont());

    return fm.stringWidth(string);
  }

  private class SizeChangeListener implements ComponentListener {
    @Override
    public void componentResized(ComponentEvent componentEvent) {
      Dimension size = componentEvent.getComponent().getSize();
      //re-create screen
      screen = new BufferedImage(size.width,size.height,BufferedImage.TYPE_INT_ARGB);
      //re-render image
      render();
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
