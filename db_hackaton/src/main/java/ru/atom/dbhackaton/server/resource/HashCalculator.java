package ru.atom.dbhackaton.server.resource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Юля on 27.03.2017.
 */
public class HashCalculator {
    public static byte[] calcHash(byte[] bytes) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-224");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such hash algo");
        }
        md.update(bytes);
        return md.digest();
    }
}
