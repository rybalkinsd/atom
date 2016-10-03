package ru.atom.client;

import ru.atom.model.Gender;
import ru.atom.model.Person;

import java.util.Collection;

/**
 * Created by s.rybalkin on 26.09.2016.
 */
public interface RestClient {
    Collection<? extends Person> getBatch(Gender gender);
}