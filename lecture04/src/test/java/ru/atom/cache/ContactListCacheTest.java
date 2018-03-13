package ru.atom.cache;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Sergey Rybalkin on 11/03/17.
 */
@Ignore
public class ContactListCacheTest {
    private ContactListCache cache1;
    private ContactListCache cache1000;

    private Person johnny = new Person("johnny", "depp");

    @Before
    public void setUp() throws Exception {
        cache1 = new ContactListCache(1);
        cache1000 = new ContactListCache(1000);
    }

    @Test
    public void cache1() throws Exception {
        assertEquals(0, cache1.getSize());
        assertEquals(1, cache1.getCapacity());

        cache1.put(johnny, Collections.singletonList(johnny));
        assertEquals(cache1.getSize(), cache1.getCapacity());

        assertTrue(cache1.get(johnny).contains(johnny));

        Person newOne = new Person("new", "one");
        List<Person> contacts = Arrays.asList(
                new Person("1", "1"),
                new Person("2", "2")
        );
        cache1.put(newOne, contacts);
        assertEquals(cache1.getSize(), cache1.getCapacity());
        assertNull(cache1.get(johnny));
        assertNotNull(cache1.get(newOne));
    }

    @Test
    public void cache1000() throws Exception {
        assertEquals(0, cache1000.getSize());
        assertEquals(1000, cache1000.getCapacity());

        List<Person> contacts = Arrays.asList(
                new Person("1", "1"),
                new Person("2", "2")
        );

        IntStream.range(0, 1000).boxed()
                .map(Object::toString)
                .forEach(x -> cache1000.put(new Person(x, x), contacts));

        assertNull(cache1000.get(johnny));
        assertNotNull(cache1000.get(new Person("50", "50")));

        cache1000.put(johnny, Collections.singletonList(johnny));
        assertNotNull(cache1000.get(johnny));
    }

}