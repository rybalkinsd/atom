package ru.atom.dbhackaton.services;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by kinetik on 16.04.17.
 */
public class PasswordHasher {

    private static int workload = 13;

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(workload);
        String hashPassword = BCrypt.hashpw(password, salt);
        return hashPassword;
    }

    public static boolean checkPassword(String password, String hashPassword) {
        boolean passwordVerified = false;
        if (hashPassword == null || !hashPassword.startsWith("$2a$")) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }
        return BCrypt.checkpw(password, hashPassword);
    }
}
