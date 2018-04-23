package ru.atom.lecture11.billing;

import java.util.concurrent.atomic.AtomicInteger;

public class Account {
    private static AtomicInteger counter = new AtomicInteger();
    private final Integer id;
    private final String name;
    private Integer money;

    public Account(String name, Integer money) {
        this.id = counter.incrementAndGet();
        this.name = name;
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
