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
        return firstName.hashCode() + 2 * familyName.hashCode() + 17;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            return ((Person) obj).familyName.equals(this.familyName) && ((Person) obj).firstName.equals(this.firstName);
        } else {
            return false;
        }
    }
}
