package ru.atom.server;

/**
 * Created by Fella on 18.04.2017.
 */
public class Experiment {
    public static void main(String[] args) {
       String tokin = "bearer 36038967713395902";
       RegisterJersey rj = new RegisterJersey();
       rj.logout(tokin);
    }
}
