package ru.atom.lecture10.billing;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Broken implementation of billing service
 * Money are lost here
 */
@Controller
@RequestMapping("billing")
public class BillingResource {
    private Map<String, Integer> userToMoney = new HashMap<>();

    /**
     * curl -XPOST localhost:8080/billing/addUser -d "user=sasha&money=100000"
     * curl -XPOST localhost:8080/billing/addUser -d "user=sergey&money=100000"
     */
    @RequestMapping(
            path = "addUser",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> addUser(@RequestParam("user") String user,
                                          @RequestParam("money") Integer money) {

        if (user == null || money == null) {
            return ResponseEntity.badRequest().body("");
        }
        userToMoney.put(user, money);

        return ResponseEntity.ok("Successfully created user [" + user + "] with money " + userToMoney.get(user) + "\n");
    }

    @RequestMapping(
            path = "sendMoney",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> sendMoney(@RequestParam("from") String fromUser,
                                            @RequestParam("to") String toUser,
                                            @RequestParam("money") Integer money) {
        if (fromUser == null || toUser == null || money == null) {
            return ResponseEntity.badRequest().body("");
        }
        if (!userToMoney.containsKey(fromUser) || !userToMoney.containsKey(toUser)) {
            return ResponseEntity.badRequest().body("No such user\n");
        }
        if (userToMoney.get(fromUser) < money) {
            return ResponseEntity.badRequest().body("Not enough money to send\n");
        }
        userToMoney.put(fromUser, userToMoney.get(fromUser) - money);
        userToMoney.put(toUser, userToMoney.get(toUser) + money);
        return ResponseEntity.ok("Send success\n");
    }

    /**
     * curl localhost:8080/billing/stat
     */
    @RequestMapping(
            path = "stat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getStat() {
        return ResponseEntity.ok(userToMoney + "\n");
    }
}
