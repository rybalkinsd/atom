package ru.atom.cache;

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
    public boolean equals(Object obj) {
        Person tmp = (Person)obj;
        if (firstName.equals(tmp.getFirstName()) && familyName.equals(tmp.getFamilyName())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() + familyName.hashCode();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }
}
