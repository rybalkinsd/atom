package ru.atom.cache;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

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
        assertThat(cache1.getSize(), is(equalTo(0)));
        assertThat(cache1.getCapacity(), is(equalTo(1)));

        cache1.put(johnny, Collections.singletonList(johnny));
        assertThat(cache1.getSize(), is(equalTo(cache1.getCapacity())));

        assertThat(cache1.get(johnny).contains(johnny), is(true));

        Person newOne = new Person("new", "one");
        List<Person> contacts = Arrays.asList(
                new Person("1", "1"),
                new Person("2", "2")
        );
        cache1.put(newOne, contacts);
        assertThat(cache1.getSize(), is(equalTo(cache1.getCapacity())));
        assertThat(cache1.get(johnny), is(nullValue()));
        assertThat(cache1.get(newOne), is(notNullValue()));
    }

    @Test
    public void cache1000() throws Exception {
        assertThat(cache1000.getSize(), is(equalTo(0)));
        assertThat(cache1000.getCapacity(), is(equalTo(1000)));

        List<Person> contacts = Arrays.asList(
                new Person("1", "1"),
                new Person("2", "2")
        );

        IntStream.range(0, 1000).boxed()
                .map(Object::toString)
                .forEach(x -> cache1000.put(new Person(x, x), contacts));

        assertThat(cache1000.get(johnny), is(nullValue()));
        assertThat(cache1000.get(new Person("50", "50")), is(notNullValue()));

        cache1000.put(johnny, Collections.singletonList(johnny));
        assertThat(cache1000.get(johnny), is(notNullValue()));
    }

}