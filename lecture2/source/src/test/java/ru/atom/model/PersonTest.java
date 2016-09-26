package ru.atom.model;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by s.rybalkin on 26.09.2016.
 */
public class PersonTest {
    private Person person;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void simpleWriteTest() {
        Person person = new Person();
    }

    @Test
    public void jsonReadWriteTest() throws Exception {

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setGender(Gender.FEMALE);
        person.setName("No name");
        person.setAge(21);
        person.setLocation(new Location(1, 1));

        Person convertedPerson = Person.readJson(person.writeJson());

        assertEquals(person, convertedPerson);
    }

}