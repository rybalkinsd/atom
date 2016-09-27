package ru.atom.controller;

import ru.atom.model.Gender;
import ru.atom.model.Image;
import ru.atom.model.Location;
import ru.atom.model.Person;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static ru.atom.model.Gender.*;

/**
 * Created by s.rybalkin on 27.09.2016.
 */
public class RestClientImplMock implements RestClient {

    private static final Person FRY;
    private static final Person LADY;
    static {
        FRY = new Person();
        FRY.setId(UUID.randomUUID());
        FRY.setName("Stephen");
        FRY.setGender(MALE);
        FRY.setAge(59);
        FRY.setLocation(new Location(51.5287718, -0.2416814));
        FRY.setDesctiption("Actor and writer currently working on new TV comedy.");

        try {
            FRY.setImage(new Image(new URL("http://hitgid.com/images/%D1%81%D1%82%D0%B8%D0%B2%D0%B5%D0%BD-%D1%84%D1%80%D0%B0%D0%B9-2.jpg"), 360, 288));
        } catch (MalformedURLException ignored) { }
        try {
            FRY.setInstagramUrl(new URL("https://www.instagram.com/stephenfryactually/"));
        } catch (MalformedURLException ignored) { }

        LADY = new Person();
    }

    @Override
    public Person next(Gender gender) {
        switch (gender){
            case FEMALE:
                return FRY;
            case MALE:
                return FRY;
        }

        return null;
    }
}
