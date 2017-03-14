package ru.atom.cache;

import java.util.HashMap;
import java.util.List;

public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {


    private HashMap<Person, List<? extends Person>> friends;


    public ContactListCache(int capacity) {
        super(capacity);
        this.friends = new HashMap<>(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (this.getSize() == this.getCapacity()) this.removeAny();
        return this.friends.put(person, people) == people;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return this.friends.get(person);
    }


    @Override
    public int getSize() {
        return this.friends.size();
    }

    private boolean removeAny() {
        for (Person removePerson : friends.keySet()) {
            friends.remove(removePerson);
            return true;
        }
        return false;
    }

}