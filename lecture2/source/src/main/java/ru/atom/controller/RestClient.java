package ru.atom.controller;

import ru.atom.model.Gender;
import ru.atom.model.Person;

/**
 * Created by s.rybalkin on 26.09.2016.
 */
public interface RestClient {
    String next(Gender gender);
}
