package ru.atom.dbhackaton.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by mac on 20.04.17.
 */

@Entity
@Table(name = "personal_result", schema = "mm")
public class UserResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Match.class)
    private Match match;

    @OneToOne(cascade = CascadeType.PERSIST, targetEntity = User.class)
    private User user;

    @Column(name = "score", nullable = false)
    private Integer score;

    public UserResult(Match match, User user, Integer score) {
        this.match = match;
        this.user = user;
        this.score = score;
    }

    @Override
    public String toString() {
        return "PersonalResult{" +
                "id=" + id +
                ", match=" + match +
                ", user=" + user +
                ", score=" + score +
                '}';
    }
}