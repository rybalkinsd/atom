package accountserver.authInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by User on 20.10.2016.
 */
@Entity
@Table(name="user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String login;
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column(nullable = true)
    private String email;

    @Transient
    private static final ObjectMapper mapper = new ObjectMapper();

    private User(){}

    public User(String login, String password){
        this.login = login;
        this.password = password;
        registrationDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        email = "";
    }

    public User(String login, String password, String email) {
        this(login, password);
        if(!email.isEmpty()){
            if(validateEmail(email)){
                this.email = email;
            }
        }
    }

    public String writeJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

    private int generateId(){
        return ThreadLocalRandom.current().nextInt();
    }

    @Override
    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(other instanceof User){
            User otherUser = (User) other;
            return this.getLogin().equals(otherUser.getLogin()) && this.password.equals(otherUser.password);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return ((login != null)? login.hashCode(): 0) + ((password != null)? password.hashCode(): 0);
    }

    @Override
    public String toString(){
        return String.format("User{id=%s,login=%s,registration_time=%s,email=%s}",
                id, login, registrationDate, email);
    }

    private boolean validateEmail(String email){
        return true;
    }


    public String getLogin() {
        return login;
    }

    public User setLogin(String name) {
        this.login= name;
        return this;
    }

    public String getPassword(){return password;}

    public User setPassword(String password){
        this.password = password;
        return this;
    }

    public int getId(){return id;}

    public String getEmail(){return email;}

    public User setEmail(String email){
        this.email = email;
        return this;
    }

    public Date getRegistrationDate(){return registrationDate;}
}
