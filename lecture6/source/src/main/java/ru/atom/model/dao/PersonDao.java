package ru.atom.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.data.Gender;
import ru.atom.model.data.person.Person;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by s.rybalkin on 17.10.2016.
 */
public class PersonDao  implements Dao<Person> {
    private static final Logger log = LogManager.getLogger(PersonDao.class);

    @Override
    public List<Person> getAll() {
        return Database.selectTransactional(session -> session.createQuery("from Person").list());
    }


    @Override
    public List<Person> getAllWhere(String ... hqlCondidtions) {
        return getAll();
    }

    @Override
    public void insert(Person person) {
        throw new NotImplementedException();
    }

}
