package accountserver.database.users;

import com.google.gson.annotations.Expose;
import main.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.jetbrains.annotations.NotNull;
import utils.idGeneration.IDGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by xakep666 on 23.10.16.
 * <p>
 * Describes user
 */
@Entity
@Table(name = "users")
@DynamicUpdate
@SelectBeforeUpdate
public class User implements Serializable {
    @NotNull
    private static String digestAlg = "sha-256";
    @NotNull
    private static Logger log = LogManager.getLogger(User.class);

    static {
        log.info("Hashing passwords with " + digestAlg);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private int id;
    @NotNull
    @Column(name = "user_name", nullable = false)
    @Expose
    private String name = "";
    @NotNull
    @Column(name = "user_password", nullable = false)
    private byte[] passwordHash = new byte[0];
    @NotNull
    @Column(name = "user_email")
    @Expose
    private String email = "";
    @NotNull
    @Column(name = "user_registration_date", nullable = false)
    @Expose()
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate = new Date();

    protected User() {
    }

    /**
     * Create new user
     *
     * @param name     user name
     * @param password user password
     */
    public User(@NotNull String name, @NotNull String password) {
        this.id = ApplicationContext.instance().get(IDGenerator.class).next();
        this.name = name;
        try {
            MessageDigest md = MessageDigest.getInstance(digestAlg);
            md.update(password.getBytes());
            passwordHash = md.digest();
        } catch (NoSuchAlgorithmException e) {
            log.fatal(e.getMessage());
            System.exit(1);
        }
        log.info(String.format("Created new user %s, id %d", name, id));
    }

    /**
     * @return user`s name
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Change user`s name
     *
     * @param newName a new name
     */
    public void setName(@NotNull String newName) {
        log.info("User " + name + " changed name to " + newName);
        name = newName;
    }

    /**
     * @return user`s id
     */
    public int getId() {
        return id;
    }

    /**
     * Validate given password for user
     *
     * @param password password to check
     * @return true if password is valid for this user, false otherwise
     */
    public boolean validatePassword(@NotNull String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(digestAlg);
            md.update(password.getBytes());
            return MessageDigest.isEqual(passwordHash, md.digest());
        } catch (NoSuchAlgorithmException e) {
            log.fatal(e.getMessage());
            System.exit(1);
        }
        return false;
    }

    /**
     * Set new password for user
     *
     * @param newPassword a new password
     */
    public void updatePassword(@NotNull String newPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance(digestAlg);
            md.update(newPassword.getBytes());
            passwordHash = md.digest();
        } catch (NoSuchAlgorithmException e) {
            log.fatal(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * @return User email
     */
    @NotNull
    public String getEmail() {
        return email;
    }

    /**
     * Update user email
     *
     * @param email new email
     */
    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    /**
     * @return User registration date
     */
    @NotNull
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Clones optional profile info
     *
     * @param user user which profile will be cloned
     */
    public void cloneProfile(@NotNull User user) {
        email = user.getEmail();
    }

    @Override
    public String toString() {
        return String.format("name: %s, id %d, email %s, registration date %s", name, id, email, registrationDate);
    }

    @Override
    public boolean equals(Object o) {
        return (this == o) || (o instanceof User) &&
                ((User) o).id == id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
