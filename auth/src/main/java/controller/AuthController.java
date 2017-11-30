package controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.AuthService;

@RestController()
@RequestMapping("auth")
public class AuthController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @RequestMapping(
            path = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> register(@RequestParam("name") String name,
                                           @RequestParam("password") String password) {
        String response = authService.register(name, password);
        if (response.equals("You are registered"))
            return new ResponseEntity<String>(response, HttpStatus.OK);
        else return new ResponseEntity<String>(response, HttpStatus.CONFLICT);
    }

    @RequestMapping(
            path = "/checkregister",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> registerCheck(@RequestParam("name") String userName) {
        if (!authService.checkLogin(userName)) {
            return new ResponseEntity<String>("Incorrect username", HttpStatus.LENGTH_REQUIRED);
        }
        if (authService.isRegistered(userName)) {
            return new ResponseEntity<String>("This name is already in use", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<String>("Username is correct", HttpStatus.OK);
    }

    @RequestMapping(
            path = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> login(@RequestParam("name") String name,
                                        @RequestParam("password") String password) {
        String response = authService.login(name, password);
        if (response.equals("You are logined!"))
            return new ResponseEntity<String>(response, HttpStatus.OK);
        else return new ResponseEntity<String>(response, HttpStatus.CONFLICT);
    }
}
