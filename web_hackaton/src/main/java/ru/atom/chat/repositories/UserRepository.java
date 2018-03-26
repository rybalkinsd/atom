package ru.atom.chat.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.atom.chat.models.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByName(String name);

    List<User> findAllByisOnline(boolean isOnline);
}
