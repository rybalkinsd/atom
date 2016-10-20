package ru.atom.client;

import ru.atom.model.data.Gender;
import ru.atom.model.data.Location;
import ru.atom.model.data.person.Person;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Controller {
    private RestClient client;


    public Controller(RestClient client, Gender lookingFor) {
        this.client = client;
    }

    public List<? extends Person> findYoungerThan29(Gender gender) {
        return Collections.emptyList();
    }

    public Map<Location, List<? extends Person>> groupByLocation(Gender gender) {
        return Collections.emptyMap();
    }
}