package ru.atom;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import java.util.List;
import ru.atom.model.GameObject;

public class GamePanel extends JPanel {
    List<GameObject> list;

    public GamePanel(List<GameObject> list) {
        setOpaque(true);
        this.list = list;
    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 100, 100);
        g.dispose();
    }
}