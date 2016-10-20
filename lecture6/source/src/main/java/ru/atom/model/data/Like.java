package ru.atom.model.data;

import javax.persistence.*;

@Entity
@Table(name = "like_entity")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int source;
    private int target;

    public Like() { }

    public Like(int source, int target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return "Like{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}