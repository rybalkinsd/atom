package model.data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by venik on 03.11.16.
 */
@Entity(name = "Tokens")
@Table(name = "Tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date date;
    {
        date = new Date();
    }
    public int getId(){return id;}
    public Date getDate(){return date;}

    public Token setId(int id){this.id=id;return this;}
    public Token setDate(Date date){this.date=date;return this;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Token{").
                append(" id = " + id).
                append(" date = "+new SimpleDateFormat("dd.MM.yyyy").format(date)).
                append("}");
        return sb.toString();
    }

}
