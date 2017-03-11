package ru.atom.cache;

import java.util.HashMap;
import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private HashMap<Person, List<? extends Person>> memory;

    public ContactListCache(int capacity) {
        super(capacity);
        this.memory = new HashMap<>(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (this.getSize() == this.getCapacity()) {
            this.removeAny();
        }
        return this.memory.put(person, people) == people;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return this.memory.get(person);
    }

    @Override
    public int getSize() {
        return this.memory.size();
    }

    private boolean removeAny() {
        Person key = this.memory.keySet().iterator().next();
        return this.memory.remove(key) != null;
    }

}
