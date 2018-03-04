package ru.atom;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println(getHelloWorld());
        System.out.println(null == null);
    }

    public static String getHelloWorld() {
        return "Hello, World!";
    }
}
