package ru.atom;

import java.util.Random;


/**
 * Created by pavel on 23.03.17.
 */
public class TockenRk {

    private static Random random = new Random();

    private TockenRk() {
    }

    /**
     * Method for generating unique tocken's*
     *
     * @return unique tocken
     */
    public static Long generateTocken() {
        Long token = null;
        boolean tokenExist = true;
        while (tokenExist) {
            token = Math.abs(random.nextLong());
            if (UserContainerRk.getUserByTocken(token) == null) {
                tokenExist = false;
            }
        }
        return token;
    }
}
