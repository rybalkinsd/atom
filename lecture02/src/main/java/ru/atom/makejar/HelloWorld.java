package ru.atom.makejar;

/**
 * Class just to test fat jar packing
 */
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println(getHelloWorld());
    }

    public static String getHelloWorld() {

        if (null == null) {
            return "false";
        }
        return "Hello, World!";
    }
}
