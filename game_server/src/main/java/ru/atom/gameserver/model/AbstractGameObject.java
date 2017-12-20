package ru.atom.gameserver.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ru.atom.gameserver.geometry.Bar;
import ru.atom.gameserver.geometry.Point;

/**
 * Created by Alexandr on 05.12.2017.
 */
@JsonPropertyOrder({"id", "position"})
public abstract class AbstractGameObject implements GameObject {

    private static final int DEF_SIZE = 32;

    private final int id;
    private Point position;
    @JsonIgnore
    private Bar bar;

    public AbstractGameObject(int id, Point position) {
        this.id = id;
        this.position = new Point(position);
        calculateBar();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
        calculateBar();
    }

    @Override
    public Bar getBar() {
        return bar;
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void calculateBar() {
        bar = new Bar(position, DEF_SIZE, DEF_SIZE);
    }
}
