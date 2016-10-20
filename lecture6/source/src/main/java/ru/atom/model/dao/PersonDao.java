package ru.atom.model.dao;

import jersey.repackaged.com.google.common.base.Joiner;
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
import java.util.Arrays;
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
    public List<Person> getAllWhere(String ... hqlConditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from Person where " + totalCondition).list());
    }

    @Override
    public void insert(Person person) {
        Database.doTransactional(session -> session.save(person));
    }

}
