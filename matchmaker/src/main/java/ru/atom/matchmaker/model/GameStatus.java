package ru.atom.matchmaker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "game_status", schema = "matchmaker")
public class GameStatus {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true, nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "status")
    private Set<Game> games;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
