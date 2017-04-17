package ru.atom.dbhackaton.server.base;


import javax.persistence.*;

@Entity
@Table(name = "personal_result", schema = "mm")
public class PersonalResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Match.class)
    private Match match;

    @OneToOne(cascade = CascadeType.PERSIST, targetEntity = User.class)
    private User user;

    @Column(name = "score", nullable = false)
    private Integer score;

    public PersonalResult(Match match, User user, Integer score) {
        this.match = match;
        this.user = user;
        this.score = score;
    }
}
