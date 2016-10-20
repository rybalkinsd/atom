package ru.atom.client;

import ru.atom.model.data.Gender;
import ru.atom.model.data.person.Person;

import java.util.Collection;

/**
 * Created by s.rybalkin on 26.09.2016.
 */
public interface RestClient {
    boolean register(String user, String password);
    Long login(String user, String password);
    Collection<Person> getBatch(Long token, Gender gender);
}