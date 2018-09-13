package ru.atom.list;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


@Ignore
public class CustomLinkedListTest {
    private List<Integer> intList = new CustomLinkedList<>();
    private List<String> stringList = new CustomLinkedList<>();

    @Before
    public void setUp() throws Exception {
        intList.add(42);
        intList.add(38);

        stringList.add("Hello");
        stringList.add(", ");
        stringList.add("world!");
    }

    @Test
    public void sizeTest() throws Exception {
        assertEquals(2, intList.size());
        assertEquals(3, stringList.size());
    }

    @Test
    public void containsTest() throws Exception {
        assertTrue(intList.contains(42));
        assertFalse(intList.contains(0));

        assertTrue(stringList.contains(", "));
        assertFalse(stringList.contains("World"));
    }

    @Test
    public void forEachTest() throws Exception {
        long sum = 0;
        for (Integer integer : intList) {
            sum += integer;
        }
        assertEquals(80L, sum);

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : stringList) {
            stringBuilder.append(s);
        }

        assertEquals("Hello, world!", stringBuilder.toString());
    }

    @Test
    public void addAll() throws Exception {
        intList.addAll(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(2 + 5, intList.size());
    }

    @Test
    public void removeTest() throws Exception {
        intList.remove((Integer) 42);
        assertFalse(intList.contains(42));
        assertTrue(intList.contains(38));
        assertEquals(1, intList.size());
    }
}