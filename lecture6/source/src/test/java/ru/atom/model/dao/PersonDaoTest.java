package ru.atom.model.dao;

import org.junit.Test;
import ru.atom.model.data.Gender;
import ru.atom.model.data.Image;
import ru.atom.model.data.Location;
import ru.atom.model.data.person.Person;

import javax.validation.constraints.AssertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;


public class PersonDaoTest {
    private PersonDao personDao = new PersonDao();
    private Person lolita;

    {
        try {
            lolita = new Person()
                    .setGender(Gender.MALE)
                    .setAge(12)
                    .setName("Lolita")
                    .setImage(new Image(new URL("http://astro.kh.ua/wp-content/uploads/2013/12/51096289.jpeg"), 858, 429))
                    .setLocation(new Location(47.6697187, -122.1565973));
        } catch (MalformedURLException ignored) { }
    }

    @Test
    public void getAllTest() throws Exception {
        System.out.println(personDao.getAll());
    }

    @Test
    public void insertTest() throws Exception {
        int before = personDao.getAll().size();
        personDao.insert(lolita);
        assertEquals(before + 1, personDao.getAll().size());
    }


    @Test
    public void findWhereTest() throws Exception {
        List<Person> oldy = personDao.getAllWhere("age between 30 and 35");
        assertTrue(
                oldy.stream()
                        .map(Person::getName)
                        .anyMatch(s -> s.equals("Marge Simpson"))
        );
    }

}