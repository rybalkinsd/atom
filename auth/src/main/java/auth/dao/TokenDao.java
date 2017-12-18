package auth.dao;

import auth.model.Token;
import auth.model.User;
import org.springframework.data.repository.CrudRepository;

public interface TokenDao extends CrudRepository<Token, Integer> {
    int countAllByToken(String token);

    Token findAllByToken(String token);

    Token findByUser(User user);
}
