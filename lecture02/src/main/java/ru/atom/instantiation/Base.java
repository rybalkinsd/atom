package ru.atom.instantiation;

public class Base {
    private static int integer1 = 1;

    private String baseClassFieldValue = "BaseClassFieldValue";

    // static init block - блок статической иниц - статич полей
    //порядок иниц сверху вниз,  то есть в нем уже видно
    static {
        System.out.println("Base: static init block " + integer1);
    }

    // init block
    {
        System.out.println("Base: init block");
        System.out.println("Base: inited field `baseClassFieldValue` = " + baseClassFieldValue);
    }

    public Base() {
        System.out.println("Base: constructor");
    }

}
