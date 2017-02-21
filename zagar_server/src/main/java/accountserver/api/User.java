package accountserver.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alex on 24.10.2016.
 */
@Entity
@Table(name = "users")
public class User {
    @NotNull
    private static final Logger log = LogManager.getLogger(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Expose
    @Column(nullable = false)
    private  String name;

    @Expose
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String email;

    public User(@NotNull String name) {
        this.name = name;
        this.date = new Date();
    }

    public void setName(String newName){
        name = newName;
    }

    public User setPassword(@NotNull String newPassword) { this.password = newPassword; return this; }

    public User setEmail(String newEmail) { this.email = newEmail; return this; }

    public int getId () { return this.id; }

    public String getName(){
        return name;
    }

    public Date getDate() { return this.date; }

    public String getPassword() { return this.password; }

    public String getEmail() { return this.email; }

    @Override
    public String toString() {
        return this.name + " " + this.date;
    }

    private static Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public static String writeJSON(ArrayList<User> toConvert){
        return gson.toJson(toConvert);
    }

    public static ArrayList<User> parseJSON(String rawJSON){
        return gson.fromJson(rawJSON, new TypeToken<ArrayList<User>>(){}.getType());
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (!User.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final User other = (User) obj;
        //log.info (name + " vs " + other.getName() );
        if (!this.name.equals(other.getName())) {
            return false;
        }
        //log.info("Correct");
        return true;
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }

    public User(){}
}
