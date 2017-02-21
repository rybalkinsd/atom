package model.data;

import javax.persistence.*;
import java.text.SimpleDateFormat;

/**
 * Created by venik on 04.11.16.
 */
@Entity(name = "Matches")
@Table(name = "Matches")
public class Match {
    @Id
    private int users;
    private int token;

    public int getUser(){return users;}
    public int getToken(){return token;}

    public Match setUser(int users){this.users=users;return this;}
    public Match setToken(int token){this.token=token;return this;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Match{").
                append(" User_id= " + users).
                append(" Token_id = "+token).
                append("}");
        return sb.toString();
    }
}
