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

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Person)) {
            return false;
        } else {
            Person temp = (Person) obj;
            return familyName.equals(temp.getFamilyName()) && firstName.equals(temp.getFirstName());
        }
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() + familyName.hashCode();
    }
}
