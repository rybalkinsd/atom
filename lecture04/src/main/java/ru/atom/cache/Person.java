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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return person.getFirstName().equals(this.getFirstName())
                && person.getFamilyName().equals(this.getFamilyName());
    }

    @Override
    public int hashCode() {
        int hash = 31 * firstName.hashCode() + familyName.hashCode();
        return hash;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }
}
