package zagar.view;

import org.jetbrains.annotations.NotNull;
import zagar.Game;
import zagar.GameConstants;
import zagar.Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;

public class Food {
    public double x, y;
    public float size;
    private int r=0, g=255, b=0;
    @NotNull
    public float sizeRender;
    public double xRender;
    public double yRender;
    public int mass;
    private float rotation = 0;

    public Food(double x, double y) {
        this.x = x;
        this.y = y;
        this.size = (float)Math.sqrt(GameConstants.FOOD_MASS/Math.PI);
        this.xRender = this.x;
        this.yRender = this.y;
        this.sizeRender = this.size;
    }

    @Override
    public boolean equals(Object another){
        if(!(another instanceof Food)){
            return false;
        } else {
            if( this.x == ((Food) another).x && this.y == ((Food) another).y){
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode(){
        return (int)(x + y * 13);
    }

    public void tick() {
        this.xRender -= (this.xRender - x) / 5f;
        this.yRender -= (this.yRender - y) / 5f;
        this.sizeRender -= (this.sizeRender - size) / 9f;
        this.mass = Math.round((this.sizeRender * this.sizeRender) / 100);
        this.rotation += (1f / (Math.max(this.mass, 20) * 2));
    }

    public void render(@NotNull Graphics2D g, float scale) {
        if (Game.player.size() > 0){
            int size = (int) ((this.sizeRender * 2f * scale) * Game.zoom);

            float avgX = 0;
            float avgY = 0;

            for (Cell c : Game.player) {
                if (c != null) {
                    avgX += c.xRender;
                    avgY += c.yRender;
                }
            }
            Color color = new Color(this.r, this.g, this.b);
            g.setColor(color);


            avgX /= Game.player.size();
            avgY /= Game.player.size();

            int x = (int) (((this.xRender - avgX) * Game.zoom) + GameFrame.size.width / 2 - size / 2);
            int y = (int) (((this.yRender - avgY) * Game.zoom) + GameFrame.size.height / 2 - size / 2);

            if (x < -size - 30 || x > GameFrame.size.width + 30 || y < -size - 30 || y > GameFrame.size.height + 30) {
                return;
            }

            int massRender = (int) ((this.size * this.size) / 100);
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

    /*public void setColor(byte r, byte g, byte b) {
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
    }*/

    @Override
    public String toString() {
        return "Food{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}