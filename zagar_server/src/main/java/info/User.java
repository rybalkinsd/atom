package info;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "users")
@IdClass(UserPrKey.class)
public class User implements Serializable{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @Id
    @NotNull
    private String name;
    @NotNull
    private String password;

    public User() {}

    public User(@NotNull String _name, @NotNull String _password){
        this.name = _name;
        this.password = _password;
    }

    @Override
    public String toString(){
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password=" + password +
                '}';
    }

    public String getName(){
        return name;
    }

    public void setName(@NotNull String _name){
        name = _name;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }
}