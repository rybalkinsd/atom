package zagar.view;

import org.jetbrains.annotations.NotNull;
import zagar.Game;
import zagar.Main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by artem on 29.11.16.
 */
public class Food {
    private int x,y;
    private int id;
    private static float size = 50;
    private int r, g, b;


    public Food(int id, int x, int y){
        this.id=id;
        this.x=x;
        this.y=y;
        this.r=255;
        this.g=100;
        this.b=100;
    }

    public Food setX(int x){
        this.x=x;
        return this;
    }

    public Food setY(int y){
        this.y=y;
        return this;
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

    public void render(@NotNull Graphics2D g, float scale) {
            Color color = new Color(this.r, this.g, this.b);
            if (scale == 1) {
                color = new Color((int) (this.r / 1.3), (int) (this.g / 1.3), (int) (this.b / 1.3));
            }
            g.setColor(color);
            int size = (int) ((this.size * 2f * scale) * Game.zoom);

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

            int x = (int) ((this.x - avgX) * Game.zoom) + GameFrame.size.width / 2 - size / 2;
            int y = (int) ((this.y - avgY) * Game.zoom) + GameFrame.size.height / 2 - size / 2;

            if (x < -size - 30 || x > GameFrame.size.width + 30 || y < -size - 30 || y > GameFrame.size.height + 30) {
                return;
            }
            g.fillOval(x,y,size,size);
    }

    @Override
    public String toString() {
        return "Food{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", size=" + size +
                '}';
    }

}
