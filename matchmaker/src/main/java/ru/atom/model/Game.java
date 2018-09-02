package ru.atom.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "games", schema = "mm")
public class Game {

    @Id
    private long id;

    @OneToMany(mappedBy = "game",  cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Player> players;

    @Column(name = "time", unique = false, nullable = false, length = 15)
    private Date time = new Date();

    public List<Player> getPlayers() {
        return this.players;
    }

    public Game setPlayers(List<Player> players) {
        this.players = players;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Game setTime(Date timestamp) {
        this.time = timestamp;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Game setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id= " + this.id +
                "}, players= [" +
                /*players.stream()
                .map(Player::toString)
                .collect(Collectors.joining(", ")) +*/
                "], timestamp= " + time +
                '}';
    }
}
