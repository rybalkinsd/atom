package ru.atom.data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URL;
import java.util.UUID;
import org.json.simple.JSONObject;

public class Person implements Externalizable {
    private UUID id;
    private Gender gender;
    private String name;
    private int age;
    private Location location;

    private String desctiption;
    private URL imageUrl;
    private URL instagramUrl;

    public JSONObject toJson() {
        return new JSONObject();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
