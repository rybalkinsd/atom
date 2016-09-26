package ru.atom.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class Person {
    private UUID id;
    private Gender gender;
    private String name;
    private int age;
    private Location location;

    private String desctiption;
    private Image image;
    private URL instagramUrl;

    private static ObjectMapper mapper = new ObjectMapper();

    public static Person readJson(String json) throws IOException {
        return mapper.readValue(json, Person.class);
    }

    public String writeJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        if (!id.equals(person.id)) return false;
        if (gender != person.gender) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (location != null ? !location.equals(person.location) : person.location != null) return false;
        if (desctiption != null ? !desctiption.equals(person.desctiption) : person.desctiption != null) return false;
        if (image != null ? !image.equals(person.image) : person.image != null) return false;
        return instagramUrl != null ? instagramUrl.equals(person.instagramUrl) : person.instagramUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + gender.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (desctiption != null ? desctiption.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (instagramUrl != null ? instagramUrl.hashCode() : 0);
        return result;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public URL getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(URL instagramUrl) {
        this.instagramUrl = instagramUrl;
    }
}
