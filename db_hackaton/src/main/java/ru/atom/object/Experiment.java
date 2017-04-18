package ru.atom.object;

/**
 * Created by Fella on 18.04.2017.
 */
public class Experiment {
    public static void main(String[] args) {
        String password = "1312h41i7yui4bwi";
        User user = new User().setPassword(password);
        /*System.out.println(user.isThatPassword(User.md5Custom(password)));*/
        /*System.out.println(User.md5Custom(password));*/
    }
}
