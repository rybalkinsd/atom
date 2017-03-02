package ru.atom.instantiation;

public class Base {
    private static int integer1 = 1;

    private String baseClassFieldValue = "BaseClassFieldValue";

    static {
        System.out.println("Base: static init block " + integer1);
    }

    {
        System.out.println("Base: init block");
        System.out.println("Base: inited field `baseClassFieldValue` = " + baseClassFieldValue);
    }

    public Base() {
        System.out.println("Base: constructor");
    }

}
