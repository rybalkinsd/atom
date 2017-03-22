package ru.atom.http.server;

/**
 * Created by ruslbizh on 22.03.2017.
 */
public class ExtraInit {
    private static ExtraInit ourInstance = new ExtraInit();

    public static ExtraInit getInstance() {
        return ourInstance;
    }

    static ExtraInit makeinit() {
        ChatResource.readFromFile();
        return null;
    }
}

