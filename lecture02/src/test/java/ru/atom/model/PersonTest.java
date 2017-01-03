package ru.atom.model;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    @Test
    public void jsonReadWriteTest() throws Exception {
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setGender(Gender.FEMALE);
        person.setName("Username");
        person.setAge(21);
        person.setLocation(new Location(1, 1));

        Person convertedPerson = Person.readJson(person.writeJson());

        assertEquals(person, convertedPerson);
    }

}