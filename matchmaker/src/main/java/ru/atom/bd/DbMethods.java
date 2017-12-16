package ru.atom.bd;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


public class DbMethods {

    private static final UserDao userDao = new UserDao();

    private static final Logger log = LogManager.getLogger(DbMethods.class);

    @RequestMapping(
            path = "firstname",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public static ResponseEntity<String> players(@RequestParam("id") long id, @RequestParam("firstname") String name1,
                                                 @RequestParam("secondname") String name2) {




        Gamesession newgamesession = new Gamesession(id, name1, name2);

        userDao.insert(newgamesession);
        log.info("[" + name1 + "] come");
        log.info("[" + name2 + "] come");
        log.info("[" + id + "] is");


        return ResponseEntity.ok().build();
    }


}
