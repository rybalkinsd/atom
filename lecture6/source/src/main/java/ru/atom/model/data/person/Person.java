package ru.atom.model.data.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.atom.model.data.Gender;
import ru.atom.model.data.Image;
import ru.atom.model.data.Location;

import javax.persistence.*;
import java.net.URL;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String name;

    private int age;

    @Embedded
    private Location location;

    @Column(nullable = true)
    private String desctiption;

    @Embedded
    private Image image;

    @Column(nullable = true)
    private URL instagramUrl;

//    private static final ObjectMapper mapper = new ObjectMapper();
//
//
//    public static Person readJson(String json) throws IOException {
//        return mapper.readValue(json, Person.class);
//    }
//
//    public String writeJson() throws JsonProcessingException {
//        return mapper.writeValueAsString(this);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (gender != person.gender) return false;
        return name != null ? name.equals(person.name) : person.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public Person setId(int id) {
        this.id = id;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Person setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Person setLocation(Location location) {
        this.location = location;
        return this;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public Person setDesctiption(String desctiption) {
        this.desctiption = desctiption;
        return this;
    }

    public Image getImage() {
        return image;
    }

    public Person setImage(Image image) {
        this.image = image;
        return this;
    }

    public URL getInstagramUrl() {
        return instagramUrl;
    }

    public Person setInstagramUrl(URL instagramUrl) {
        this.instagramUrl = instagramUrl;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", location=" + location +
                ", desctiption='" + desctiption + '\'' +
                ", image=" + image +
                ", instagramUrl=" + instagramUrl +
                '}';
    }
}