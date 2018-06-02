package ru.atom.cache;


import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private HashMap map = new HashMap();

    public ContactListCache(int capacity) {
        super(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (getSize() >= capacity) {
            do {
                removeAny();
            } while (getSize() - capacity == 0);
        }

        if (!map.containsKey(person)) {
            map.put(person, people);
            return true;
        } else return false;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return (List<? extends Person>) map.get(person);
    }

    @Override
    public int getSize() {
        return map.size();
    }

    private void removeAny() {
        Random random = new Random();
        List<Person> keys = new ArrayList(map.keySet());
        Person randomPerson = keys.get(random.nextInt(keys.size()));
        map.remove(randomPerson);
    }
}
