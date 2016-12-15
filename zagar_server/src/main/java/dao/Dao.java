package dao;

import java.util.List;

interface Dao<T> {
    List<T> getAll();
    List<T> getAllWhere(String... hqlConditions);
    void insert(T t);
}