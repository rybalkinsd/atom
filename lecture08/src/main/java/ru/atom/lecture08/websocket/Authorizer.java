package ru.atom.lecture08.websocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.atom.lecture08.websocket.dao.MessageDao;
import ru.atom.lecture08.websocket.dao.UserDao;
import ru.atom.lecture08.websocket.model.Message;
import ru.atom.lecture08.websocket.model.Topic;
import ru.atom.lecture08.websocket.model.User;

@Controller
@RequestMapping
public class Authorizer {

    @Autowired
    private UserDao dao;

    @Autowired
    private MessageDao msgDao;
    /**
     * curl -X POST -i localhost:8090/signUp -d "name=Abi&passw=201998"
     */

    @RequestMapping(
            path = "signUp",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> signUp(@RequestParam("name") String name, @RequestParam("passw") String passw) {
        if (passw.length() < 6 || !passw.matches("^[A-Za-z0-9-]+$"))
            return ResponseEntity.badRequest().body("Your password should contain at least 6 " +
                    "symbols and consist of Latin letters and digits!");
        else {
            if (dao.getByLogin(name) != null)
                return ResponseEntity.badRequest().body("User with this login already exists!");
            else {
                dao.save(new User().setLogin(name).setPassword(passw).setOnline((short)0));
                return ResponseEntity.ok("You've successfully signed up!");
            }
        }

    }

    /**
     * curl -X POST -i localhost:8090/login -d "name=Abi&passw=201998"
     */


    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name, @RequestParam("passw") String passw) {
        User user = dao.getByLogin(name);
        if (user == null)
            return ResponseEntity.badRequest().body("User with this name has not signed up yet!");
        else if (!user.getPassword().equals(passw))
            return ResponseEntity.badRequest().body("Wrong password!");
        else if (user.getOnline() == 1) {
            return ResponseEntity.badRequest().body("User is already loggined!");
        } else {
            msgDao.save(new Message(Topic.Login,"[" + name + "]: logged in").setUser(user));
            dao.setLoggedIn(user);
            return ResponseEntity.ok().build();
        }
    }

    /**
     * curl -X POST -i localhost:8090/logout -d "name=Abi"
     */

    @RequestMapping(
            path = "logout",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity logout(@RequestParam("name") String name) {
        User user = dao.getByLogin(name);
        if (user == null)
            return ResponseEntity.badRequest().body("Not online");
        else {
            msgDao.save(new Message(Topic.Logout,"[" + name + "]:  logged out").setUser(user));
            dao.setLoggedOut(user);
            return ResponseEntity.ok("logged out");
        }
    }


}
