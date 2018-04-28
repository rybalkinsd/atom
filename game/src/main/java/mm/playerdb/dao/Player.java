package mm.playerdb.dao;


import org.springframework.stereotype.Repository;

import javax.persistence.*;


@Entity
@Table(schema = "game", name = "players")
public class Player implements Comparable<Player> {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "login",unique = true,length = 20, nullable = false)
    private String login;

    @Column(name = "pwd", length = 20, nullable = false)
    private String pwd;

    @Column(name = "rating",nullable = false)
    private int rating;

    public Player() {}

    public Player(String name, String password) {
        this.rating = 1000;
        this.login = name;
        this.pwd = password;
    }

    public int compareTo(Player p){
        return Integer.compare(this.rating, p.getRating());
    }

    public void changeRating(int ratingChange) {
        rating += ratingChange;
    }

    public int getRating() {
        return rating;
    }

    public String getName() {
        return login;
    }

}
