package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    public ContactListCache(int capacity) {
        super(capacity);
    }
    private HashMap<Person, List<? extends Person>> personList = new HashMap<>();
    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if(this.personList.size() > (this.capacity -1 )) {
            this.removeAny();
            this.personList.put(person, people);
            return true;
        } else {
            this.personList.put(person, people);
            return true;
        }
    }

    @Override
    public List<? extends Person> get(Person person) {
        return this.personList.get(person);
    }

    @Override
    public int getSize() {
        return this.personList.size();
    }

    private boolean removeAny() {
        this.personList.remove(this.personList.keySet().iterator().next());
        return true;
    }

}
