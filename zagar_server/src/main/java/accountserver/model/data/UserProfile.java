package accountserver.model.data;

import accountserver.misc.Sensitive;
import accountserver.misc.UserCanChange;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by eugene on 10/9/16.
 */

@Entity(name = "Profiles")
@Table(name = "users")
public class UserProfile {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @UserCanChange
    @Column(name = "login")
    @NaturalId
    private String email;

    @Column(name = "registration_date", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate = new Date();

    @UserCanChange
    @Column(name = "user_name")
    private String name;

    @Sensitive
    @UserCanChange
    @Column(nullable = false)
    private String password;

    @Version
    private Long version;

    public UserProfile() {
    }

    public UserProfile(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean checkCredentials(String email, String pass){
        return getEmail().equals(email) && getPassword().equals(pass);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfile that = (UserProfile) o;

        return getEmail().equals(that.getEmail());

    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
}
