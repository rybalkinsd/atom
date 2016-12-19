package model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sl on 19.10.2016.
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Date registration;  // ! не предоставлять api для изменения даты регистрации !
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    private Long id_token;

    public User(@NotNull String name, @NotNull String password){
        this.email=name;
        this.password=password;
        this.name=name;
        registration=new Date();
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public void setName(String email) {
        this.email=email;
    }

    public String getName() {
        return email;
    }

    public boolean equals(User user) {
        return user.email.equals(this.email);
    }
    @Override
    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(obj == null)
            return false;
        if(!(getClass() == obj.getClass()))
            return false;
        User tmp = (User)obj;
        if(tmp.email.equals(this.email))
            return true;
        return false;
    }

    @Override
    public int hashCode(){
        return email.length();
    }

    @Override
    public String toString(){
        return "email= "+email
                +"; password= "+password
                + "; name= "+name
                +"; regtime "+registration;
    }

    public String getNikname() {
        return name;
    }

    public void setNikname(String nikname) {
        this.name = nikname;
    }

    public Long getId_token() {
        return id_token;
    }

    public void setId_token(Long id_token) {
        this.id_token = id_token;
    }

    public Long getId() {
        return id;
    }
}
