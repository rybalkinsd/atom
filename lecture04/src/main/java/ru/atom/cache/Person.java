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


    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    @Override
    public int hashCode() {

        return getFirstName().hashCode() + getFamilyName().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        Person person = (Person) o;
        return (this.getFamilyName().equals(person.getFamilyName())
                && this.getFirstName().equals(person.getFirstName()));

    }

}