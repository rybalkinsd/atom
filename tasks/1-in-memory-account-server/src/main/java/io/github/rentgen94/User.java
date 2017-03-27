package io.github.rentgen94;

/**
 * Created by Western-Co on 26.03.2017.
 */
public class User {
    private String name;
    private int password;

    public User(String name, String password) {
        this.name = name;
        this.password = password.hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        User other = (User) o;
        if (!name.equals(other.getName())) return false;
        if (password != (other.getPassword())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return getName().hashCode() + getPassword();
    }

    public String getInJson() {
        String json = "{ " +
                "\"name\":\"" +  getName() + "\", " +
                "\"password\":\"" +  getPassword() + "\" " +
                "}";
        return json;
    }
}
