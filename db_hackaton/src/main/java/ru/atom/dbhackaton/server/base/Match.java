package ru.atom.dbhackaton.server.base;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "match", schema = "mm")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "match_date", nullable = false)
    private Date date;

    public Match() {
        this.date = new Date(System.currentTimeMillis());
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
