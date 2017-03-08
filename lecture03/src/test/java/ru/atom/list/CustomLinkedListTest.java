package ru.atom.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

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
        assertThat(intList.size(), is(equalTo(2)));
        assertThat(stringList.size(), is(equalTo(3)));
    }

    @Test
    public void containsTest() throws Exception {
        assertThat(intList.contains(42), is(true));
        assertThat(intList.contains(0), is(false));

        assertThat(stringList.contains(", "), is(true));
        assertThat(stringList.contains("World"), is(false));
    }

    @Test
    public void forEachTest() throws Exception {
        long sum = 0;
        for (Integer integer : intList) {
            sum += integer;
        }
        assertThat(sum, is(equalTo(80L)));

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : stringList) {
            stringBuilder.append(s);
        }

        assertThat(stringBuilder.toString(), is(equalTo("Hello, world!")));
    }

    @Test
    public void addAll() throws Exception {
        intList.addAll(Arrays.asList(1, 2, 3, 4, 5));
        assertThat(intList.size(), is(equalTo(2 + 5)));
    }

    @Test
    public void removeTest() throws Exception {
        intList.remove((Integer) 42);
        assertThat(intList.contains(42), is(false));
        assertThat(intList.contains(38), is(true));
        assertThat(intList.size(), is(1));
    }

    @Test
    public void indexOfTest() throws Exception {
        assertThat(intList.indexOf(42), is(0));
        assertThat(intList.indexOf(38), is(1));
        assertThat(stringList.indexOf("Hello"), is(0));
        assertThat(stringList.indexOf(", "), is(1));
        assertThat(stringList.indexOf("world!"), is(2));
        assertThat(intList.indexOf(0), is(-1));
    }

    @Test
    public void containsAllTest() throws Exception {
        assertThat(intList.containsAll(Arrays.asList(42, 38)), is(true));
        assertThat(intList.containsAll(Arrays.asList(42, 0)), is(false));
        assertThat(stringList.containsAll(Arrays.asList("Hello", ", ")), is(true));
        assertThat(stringList.containsAll(Arrays.asList("Hello", "world")), is(false));
    }
}