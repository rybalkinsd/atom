package ru.atom.exception;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by sergey on 3/4/17.
 */
public class ArrayListTest {
    private List<Integer> list = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        list.add(1);
        list.add(2);
        list.addAll(Arrays.asList(2, 3, 4, 5, 6));
        list.remove(new Integer(2));
    }

    @Test
    public void testSimpleHandle() throws Exception {
        assertThat(list.size(), is(equalTo(6)));
        assertThat(list.get(2), is(equalTo(3)));
        assertThat(list.contains(42), is(false));
    }

}
