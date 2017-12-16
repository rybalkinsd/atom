package ru.atom.bd;


/**
 * Created by sergey on 3/25/17.
 */
public class Gamesession {
    private long id;
    private String firstname;
    private String secondname;

    public long getId() {
        return id;
    }

    public Gamesession setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public Gamesession setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public Gamesession setSecondname(String secondname) {
        this.secondname = secondname;
        return this;
    }

    public Gamesession(long id, String firstname, String secondname) {
        this.id = id;
        this.firstname = firstname;
        this.secondname = secondname;
    }


}
