package ru.atom.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergey Rybalkin on 11/03/17.
 */
public class Person {
    private final String firstName;
    private final String familyName;
    //public int hash = 0;

    public Person(String firstName, String familyName) {
        this.firstName = firstName;
        this.familyName = familyName;
    }

    /*public int hashCode(){
        hash = firstName.hashCode() + familyName.hashCode();
        return hash;
    }*/



    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (getFirstName() != null ? !getFirstName().equals(person.getFirstName()) : person.getFirstName() != null)
            return false;
        return getFamilyName() != null ? getFamilyName().equals(person.getFamilyName()) : person.getFamilyName()
                == null;
    }

    @Override
    public int hashCode() {
        int result = getFirstName() != null ? getFirstName().hashCode() : 0;
        result = 31 * result + (getFamilyName() != null ? getFamilyName().hashCode() : 0);
        return result;
    }
}
