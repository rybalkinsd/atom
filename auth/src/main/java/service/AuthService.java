package service;

import dao.TokenDao;
import dao.UserDao;
import model.Token;
import model.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.security.SecureRandom;

@Service
public class AuthService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;

    @Transactional
    public String register(@NotNull String name, @NotNull String password) {
        log.info("register");
        if(userDao.findByLogin(name) == null) {
            User user = new User();
            userDao.save(user.setLogin(name)
                    .setPasshash(generatePasshash(password)));
            return "You are registered";
        } else {
            return "This name is already used";
        }
    }

    @Transactional
    public String login(@NotNull String name, @NotNull String password) {
        if(userDao.findByLogin(name) != null) {
            String passhash = userDao.findPasshashByLogin(name).getPasshash();
            if (tokenDao.findByUser(userDao.findByLogin(name)) == null) {
                if (BCrypt.checkpw(password, passhash)) {
                    Token token = new Token();
                    token.setUser(userDao.findByLogin(name)).setToken(generateToken());
                    tokenDao.save(token);
                    return "You are logined!";
                } else {
                    return "Wrong password";
                }
            } else {
                return "You are already logined";
            }
        } else {
            return "You are not registered";
        }
    }

    @Transactional
    public boolean isRegistered(String name) {
        return userDao.findByLogin(name) == null;
    }

    public boolean checkLogin(String name) {
        return name.length() > 1 && name.length() < 20;
    }

    private String generateToken() {
        final SecureRandom random = new SecureRandom();
        String token = String.valueOf(random.nextLong());
        while(validateToken(token)) {
            token = String.valueOf(random.nextLong());
        }
        return token;
    }

    private boolean validateToken(String token) {
        return tokenDao.findAllByToken(token) != null;
    }

    private String generatePasshash(String password) {
        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
        return pw_hash;
    }

    private boolean checkPassword(String passHash, String stored_hash) {
        return BCrypt.checkpw(passHash, stored_hash);
    }

}