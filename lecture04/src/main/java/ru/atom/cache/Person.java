package ru.atom.cache;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sergey Rybalkin on 11/03/17.
 */
public class Person {
    private final String firstName;
    private final String familyName;

    public Person(String firstName, String familyName) {
        this.firstName = firstName;
        this.familyName = familyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!firstName.equals(person.firstName)) return false;
        return familyName.equals(person.familyName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + familyName.hashCode();
        return result;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }
}
