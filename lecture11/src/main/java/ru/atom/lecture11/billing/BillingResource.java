package ru.atom.lecture11.billing;

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
    private Map<String, Account> userToMoney = new HashMap<>();

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
                                          @RequestParam(value = "money", defaultValue = "0") Integer money) {

        if (user == null) {
            return ResponseEntity.badRequest().body("");
        }
        userToMoney.put(user, new Account(user, money));

        return ResponseEntity.ok("Successfully created user [" + user + "] with money "
                + userToMoney.get(user).getMoney() + "\n");
    }

    @RequestMapping(
            path = "sendMoney",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> sendMoney(@RequestParam("from") String fromUser,
                                            @RequestParam("to") String toUser,
                                            @RequestParam(value = "money", defaultValue = "0") Integer money) {
        if (fromUser == null || toUser == null) {
            return ResponseEntity.badRequest().body("");
        }
        if (!userToMoney.containsKey(fromUser) || !userToMoney.containsKey(toUser)) {
            return ResponseEntity.badRequest().body("No such user\n");
        }

        Account fromUserAcc = userToMoney.get(fromUser);
        if (fromUserAcc.getMoney() < money) {
            return ResponseEntity.badRequest().body("Not enough money to send\n");
        }
        synchronized (this) {
            fromUserAcc.setMoney(fromUserAcc.getMoney() - money);
            userToMoney.get(toUser).setMoney(userToMoney.get(toUser).getMoney() + money);
        }
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
