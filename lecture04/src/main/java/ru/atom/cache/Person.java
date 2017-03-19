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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        if (firstName != null && person.firstName != null && !firstName.equals(person.firstName))
            return false;
        return familyName != null &&  person.familyName != null && familyName.equals(person.familyName);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 69 * result + (familyName != null ? familyName.hashCode() : 0);
        return result;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }
}
