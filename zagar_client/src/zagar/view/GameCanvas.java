package zagar.view;

import zagar.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameCanvas extends JPanel {
  private BufferedImage screen;
  private Font font = new Font("Ubuntu", Font.BOLD, 30);
  private Font fontLB = new Font("Ubuntu", Font.BOLD, 25);
  public Font fontCells = new Font("Ubuntu", Font.BOLD, 18);

  public GameCanvas() {
    screen = new BufferedImage(GameFrame.frame_size.width, GameFrame.frame_size.height,
            BufferedImage.TYPE_INT_ARGB);
    setFont(font);
    setSize(GameFrame.frame_size);
    setVisible(true);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    render();
  }

  public void render() {
    Graphics2D g = (Graphics2D)screen.getGraphics();
    g.setColor(new Color(255, 255, 255));
    g.fillRect(0, 0, GameFrame.frame_size.width, GameFrame.frame_size.height);
    g.setColor(new Color(0, 0, 0));
    g.setFont(fontCells);
    if (Game.food != null) {
      for (int i3 = 0; i3 < Game.food.length; i3++) {
        Food food = Game.food[i3];
        if (food != null) {
          food.render(g);
        }
      }
    }
    if (Game.cells != null) {
      for (int i2 = 0; i2 < Game.cells.length; i2++) {
        Cell cell = Game.cells[i2];
        if (cell != null) {
          cell.render(g);
          if (cell.mass > 9) {
            cell.render(g);
          }
        }
      }
    }
    g.setFont(font);
    String scoreString = "Score: " + Game.score;
    g.setColor(new Color(0, 0, 0, 0.5f));
    g.fillRect(GameFrame.frame_size.width - 202, 10, 184, 265);
    g.fillRect(7, GameFrame.frame_size.height - 85, getStringWidth(g, scoreString) + 26, 47);
    g.setColor(Color.WHITE);
    g.drawString(scoreString, 20, GameFrame.frame_size.height - 50);
    int i = 0;
    g.setFont(fontLB);
    g.drawString("Leaderboard", GameFrame.frame_size.width - 110 -
            getStringWidth(g, "Leaderboard") / 2, 40);
    g.setFont(fontCells);
    if (Game.leaderBoard != null) {
      for (String s : Game.leaderBoard) {
        if (s != null) {
          g.drawString(s, GameFrame.frame_size.width - 110 -
                  getStringWidth(g, s) / 2, 40 + 22 * (i + 1));
        }
        i++;
      }
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
}