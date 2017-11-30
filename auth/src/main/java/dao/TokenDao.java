package dao;

import model.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenDao extends CrudRepository<Token, Integer> {
    int countAllByToken(String token);

    Token findAllByToken(String token);

    Token findByUser(User user);
}
