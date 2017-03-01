package ru.atom.instantiation;

public class Sub extends Base {

    private int willBeInited = 42;

    static {
        System.out.println("Sub: static init block");
    }

    {
        System.out.println("Sub: init block");
        System.out.println("Sub: inited field `willBeInited` = " + willBeInited);
        // Не можем получить значение willBeInitedLater в этом месте
    }

    private double willBeInitedLater = 40.4;

    public Sub() {
        // omitted call super() constructor
        System.out.println("Sub: constructor");
    }
}