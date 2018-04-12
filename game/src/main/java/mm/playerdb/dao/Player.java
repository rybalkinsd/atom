package mm.playerdb.dao;


import javax.persistence.*;

@Entity
@Table(name = "playersDb")
public class Player {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "login",unique = true,length = 20, nullable = false)
    private String login;

    @Column(name = "pwd", length = 20, nullable = false)
    public String pwd;

    @Column(name = "rating",nullable = false)
    private int rating;

    public Player() {}

    public Player(String name, String password) {
        this.rating = 1000;
        this.login = name;
        this.pwd = password;
    }

    public void changeRating(int ratingChange) {
        rating += ratingChange;
    }

    public long getRating() {
        return rating;
    }

    public String getName() {
        return login;
    }

}
