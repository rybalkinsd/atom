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
import java.util.Optional;

/**
 * Created by s.rybalkin on 17.10.2016.
 */
public class PersonDao  implements Dao<Person> {
    private static final Logger log = LogManager.getLogger(PersonDao.class);

    private static final String SELECT_ALL_PERSONS =
            "SELECT * FROM persons";

    @Override
    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_PERSONS);
            while (rs.next()) {
                persons.add(mapToPerson(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }

        return persons;
    }


    @Override
    public List<Person> getAllWhere(String ... conditions) {
        return getAll();
    }

    @Override
    public void insert(Person person) {
        throw new NotImplementedException();
    }

    private static Person mapToPerson(ResultSet rs) throws SQLException {
        return new Person()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setGender(Gender.valueOf(rs.getString("gender")))
                .setAge(rs.getInt("age"));
    }
}
