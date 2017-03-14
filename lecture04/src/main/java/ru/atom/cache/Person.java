package ru.atom.cache;

/**
 * Created by Sergey Rybalkin on 11/03/17.
 */

public class Person {
    private final String firstName;
    private final String secondName;

    public Person(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Person))
            return false;
        Person person = (Person) o;
        return this.getFirstName().equals(person.getFirstName())
                && this.getSecondName().equals(person.getSecondName());
    }

    @Override
    public int hashCode() {
        return getFirstName().hashCode() + getSecondName().hashCode();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }
}
