package ru.atom.cache;

/**
 * Created by Sergey Rybalkin on 11/03/17.
 */
public class Person {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        return familyName != null ? familyName.equals(person.familyName) : person.familyName == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (familyName != null ? familyName.hashCode() : 0);
        return result;
    }

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
}
