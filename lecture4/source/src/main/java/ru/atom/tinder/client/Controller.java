package ru.atom.tinder.client;

import ru.atom.tinder.model.Gender;
import ru.atom.tinder.model.Location;
import ru.atom.tinder.model.person.Person;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Controller {
    private RestClient client;


    public Controller(RestClient client, Gender lookingFor) {
        this.client = client;
    }

    public List<? extends Person> findYoungerThan29(Gender gender) {
        throw new NotImplementedException();
    }

    public Map<Location, List<? extends Person>> groupByLocation(Gender gender) {
        throw new NotImplementedException();
    }
}