package ru.atom.makejar;

/**
 * Class just to test fat jar packing
 */
public class HelloWorld {
    public static String getHelloWorld() {
        return "Hello, World!";
    }

    public static void main(String[] args) {
        System.out.println(getHelloWorld());
        System.out.println(null == null);
    }
}
