package zagar.view;

import org.jetbrains.annotations.NotNull;
import zagar.Game;
import zagar.GameConstants;

import java.awt.*;

/**
 * Created by Max on 15.12.2016.
 */
public class Virus {
    public double x, y;
    public float size;
    private int r=255, g=0, b=0;
    @NotNull
    public float sizeRender;
    public double xRender;
    public double yRender;
    public int mass;
    private float rotation = 0;

    public Virus(double x, double y) {
        this.x = x;
        this.y = y;
        this.size = (float)Math.sqrt(GameConstants.FOOD_MASS/Math.PI);
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
    }

    public void render(@NotNull Graphics2D g, float scale) {
        if (Game.player.size() > 0) {
            Color color = new Color(this.r, this.g, this.b);
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

            int x = (int) (((this.xRender - avgX) * Game.zoom) + GameFrame.size.width / 2 - size / 2);
            int y = (int) (((this.yRender - avgY) * Game.zoom) + GameFrame.size.height / 2 - size / 2);

            if (x < -size - 30 || x > GameFrame.size.width + 30 || y < -size - 30 || y > GameFrame.size.height + 30) {
                return;
            }

            int massRender = (int) ((this.size * this.size) / 100);
            /*Polygon hexagon = new Polygon();
            int a = 2 * (massRender / 8 + 10);
            a = Math.min(a, 100);
            for (int i = 0; i < a; i++) {
                float pi = 3.14f;
                int spike = 0;
                if (i % 2 == 0) {
                    spike = (int) (20 * Math.min(Math.max(1, (massRender / 80f)), 8) * Game.zoom);
                }
                hexagon.addPoint((int) ((x + (size + spike) / 2) * Math.cos(-rotation + i * 2 * pi / a) + size / 2), (int) ((y + ((size + spike) / 2) * Math.sin(-rotation + i * 2 * pi / a) + size / 2)));
            }*/
            Polygon hexagon = new Polygon();
            int a = massRender / 20 + 5;
            a = Math.min(a, 50);
            for (int i = 0; i < a; i++) {
                float pi = 3.14f;
                int pointX = (int) ((x + (size / 2) * Math.cos(rotation + i * 2 * pi / a)) + size / 2);
                int pointY = (int) ((y + (size / 2) * Math.sin(rotation + i * 2 * pi / a)) + size / 2);
                hexagon.addPoint(pointX, pointY);
            }
            g.fillPolygon(hexagon);

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
        return "Food{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
