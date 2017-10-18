package ru.atom;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import java.util.List;
import ru.atom.model.GameObject;


public class Window extends JFrame {
    public Window(List<GameObject> list) {
        //в конструкторе определяем все о в последующем создаваемом окне
        super("MyOWnBomberman");
        //создаем JPanel с нужной раскладкой
        JPanel panel = new JPanel(new BorderLayout());
        //привязываем основную панель к окну
        setContentPane(panel);
        //теперь создадим панель с отрисовкой и вставим в раскладку
        panel.add(new GamePanel(list), BorderLayout.CENTER);
        //размер установим
        setSize(new Dimension(500, 500));
    }
}

