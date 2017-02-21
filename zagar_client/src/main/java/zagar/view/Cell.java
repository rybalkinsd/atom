package main.java.zagar.view;

import main.java.zagar.Game;
import main.java.zagar.Main;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Cell {
    private final boolean virus;
    private final boolean food;
    public int id;
    public float size;
    double xRender;
    double yRender;
    int mass;
    private double x, y;
    @NotNull
    private String name = "";
    private float sizeRender;
    @NotNull
    private Color color;

    public Cell(double x, double y, float size, int id, boolean isVirus, boolean isFood) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.id = id;
        this.virus = isVirus;
        this.food = isFood;
        this.xRender = this.x;
        this.yRender = this.y;
        this.sizeRender = this.size;
        if (isVirus) {
            color = Color.GREEN;
        } else if (isFood) {
            color = generateColor(); //TODO: randomly choose from list
        } else {
            color = generateColor();
        }
    }

    public void tick() {
        this.xRender -= (this.xRender - x) / 5f;
        this.yRender -= (this.yRender - y) / 5f;
        this.sizeRender -= (this.sizeRender - size) / 9f;
        this.mass = Math.round((this.sizeRender * this.sizeRender) / 100);

        if (Game.cellNames.containsKey(this.id)) {
            this.name = Game.cellNames.get(this.id);
        }
    }

    public Color generateColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    void render(@NotNull Graphics2D g, float scale) {
        GameFrame frame = Main.getFrame();
        g.setColor(color);
        if (Game.getPlayers().size() > 0) {
            int size = (int) ((this.sizeRender * 2f * scale) * Game.zoom);

            float avgX = 0;
            float avgY = 0;

            for (Cell c : Game.getPlayers()) {
                if (c != null) {
                    avgX += c.xRender;
                    avgY += c.yRender;
                }
            }

            avgX /= Game.getPlayers().size();
            avgY /= Game.getPlayers().size();

            int x = (int) ((this.xRender - avgX) * Game.zoom) + frame.getSize().width / 2 - size / 2;
            int y = (int) ((this.yRender - avgY) * Game.zoom) + frame.getSize().height / 2 - size / 2;
//            if (!this.virus && !this.food) {
//                x = (int) ((this.xRender - avgX / 2) * Game.zoom) + frame.getSize().width / 2 - size / 2;
//                y = (int) ((this.yRender - avgY / 2) * Game.zoom) + frame.getSize().height / 2 - size / 2;
//            }
            if (x < -size - 30 || x > frame.getWidth() + 30 ||
                    y < -size - 30 || y > frame.getHeight() + 30) {
                return;
            }
            Ellipse2D figure = new Ellipse2D.Double(x, y, size, size);
            Polygon polygon = new Polygon();
            if (!this.food)
                g.fill(figure);
            else {
                for (int i = 0; i < 6; i++)
                    polygon.addPoint((int) (x + 10 * Math.cos(i * 2 * Math.PI / 6)),
                            (int) (y + 10 * Math.sin(i * 2 * Math.PI / 6)));
                g.fill(polygon);
            }
        }

        if (this.name.length() > 0 || (this.mass > 30 && !this.virus)) {
            Font font = frame.getCanvas().fontCells;
            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            FontMetrics fm = img.getGraphics().getFontMetrics(font);

            int fontSize = fm.stringWidth(this.name);

            outlineString(g, this.name, (int) (x + size / 2 - fontSize / 2), (int) (y + size / 2));

            String mass = this.mass + "";

            int massSize = fm.stringWidth(mass);

            outlineString(g, mass, (int) (x + size / 2 - massSize / 2), (int) (y + size / 2 + 17));
        }
    }

    public float getSize() {
        return size;
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
}