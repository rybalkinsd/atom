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

    // your code here
    @Override
    public int hashCode() {
        return getFirstName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        Person that = (Person) obj;
        if (this.getFamilyName().equals(that.getFamilyName())
                && this.getFirstName().equals(that.getFirstName())) {
            return true;
        }
        return false;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }
}
