package ru.atom.model.dao;

import ru.atom.model.data.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Optional;

/**
 * Created by s.rybalkin on 17.10.2016.
 */
public class MatchDao implements Dao<Match> {

    @Override
    public List<Match> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Match> getAllWhere(String... conditions) {
        throw new NotImplementedException();
    }

    @Override
    public void insert(Match match) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Match> findById(int id) {
        throw new NotImplementedException();
    }

}
