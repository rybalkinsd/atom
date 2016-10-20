package ru.atom.model.dao;

import org.junit.Test;
import ru.atom.model.data.Gender;
import ru.atom.model.data.person.Person;

import javax.validation.constraints.AssertTrue;

import java.util.List;

import static org.junit.Assert.*;


public class PersonDaoTest {
    private PersonDao personDao = new PersonDao();
    private Person lolita = new Person()
            .setGender(Gender.MALE)
            .setAge(12)
            .setName("Lolita");

    @Test
    public void getAllTest() throws Exception {
        assertTrue(personDao.getAll().size() > 0);
    }

    @Test
    public void insertTest() throws Exception {
        int before = personDao.getAll().size();
        personDao.insert(lolita);
        assertEquals(before + 1, personDao.getAll().size());
    }


    @Test
    public void findWhereTest() throws Exception {


        List<Person> oldy = personDao.getAllWhere("age BETWEEN 30 AND 35");
        assertTrue(
                oldy.stream()
                        .map(Person::getName)
                        .anyMatch(s -> s.equals("Marge Simpson"))
        );
    }

}