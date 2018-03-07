package ru.atom.exception;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println(getHelloWorld());
    }

    public static String getHelloWorld() {
        throw new NullPointerException("Ой всё");
    }
}