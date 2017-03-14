package ru.atom.cache;

import java.util.List;
import java.util.HashMap;

public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private HashMap<Person, List<? extends Person>> contacts;

    public ContactListCache(int capacity) {
        super(capacity);
        this.contacts = new HashMap<>(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (this.getSize() == this.getCapacity())
            this.removeAny();
        return this.contacts.put(person, people) == people;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return contacts.get(person);
    }

    @Override
    public int getSize() {
        return contacts.size();
    }

    private boolean removeAny() {
        for (Person person : contacts.keySet()) {
            contacts.remove(person);
            return true;
        }
        return false;
    }
}
