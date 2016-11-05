package model.data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by venik on 03.11.16.
 */
@Entity(name = "Users")
@Table(name = "Users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String mail;

    @Temporal(value=TemporalType.DATE)
    private Date date;

    {
        date = new Date();
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public String getPassword(){return password;}
    public String getMailil(){return mail;}
    public Date getDate(){return date;}


    public User setId(int id){this.id=id;return this;}
    public User setName(String name){this.name=name;return this;}
    public User setPassword(String password){this.password=password;return this;}
    public User setMail(String mail){this.mail=mail;return this;}
    public Date setDate(Date date){this.date=date;return date;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("User{").
                append("name = "+name).
                append(" id = " + id).
                append(" date = "+new SimpleDateFormat("dd.MM.yyyy").format(date));

        if(mail!=null)
            sb.append(" mail = "+mail);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return id;
    }

}
