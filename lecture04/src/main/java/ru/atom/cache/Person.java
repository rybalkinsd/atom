package ru.atom.cache;

public class Person {
    private final String firstName;
    private final String familyName;

    public Person(String firstName, String familyName) {
        this.firstName = firstName;
        this.familyName = familyName;
    }

    @Override
    public int hashCode() {
        return firstName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        Person person = (Person) o;
        return firstName.equals(person.getFirstName()) && familyName.equals(person.getFamilyName());
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }
}
