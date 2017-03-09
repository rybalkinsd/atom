package ru.atom.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
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
    public void clearTest() throws Exception {
        intList.add(42);
        intList.add(99);
        intList.clear();
        assertThat(intList.size(), is(equalTo(0)));
    }

    @Test
    public void containsAllTest() throws Exception {
        intList.add(42);
        intList.add(43);
        intList.add(42);
        assertThat(intList.containsAll(Arrays.asList(43, 2, 3, 42, 5)), is(false));
        assertThat(intList.containsAll(Arrays.asList(42, 43)), is(true));
        assertThat(intList.containsAll(Arrays.asList(43, 43, 43)), is(true));
    }

    @Test
    public void indexOfTest() throws Exception {
        assertThat(intList.indexOf(42), is(0));
        assertThat(intList.indexOf(38), is(1));
    }

    @Test
    public void addAtPositionTest() throws Exception {
        intList.add(42);
        intList.add(43);
        intList.add(42);
        intList.add(3, 91);
        assertThat(intList.indexOf(91), is(3));
        intList.add(5, 77);
        assertThat(intList.indexOf(77), is(5));
    }

    @Test
    public void removeAtPositionTest() throws Exception {
        assertThat(intList.remove(1), is(38));
        assertThat(stringList.remove(1), is(", "));
    }

    @Test
    public void subListTest() throws Exception {
        intList.add(1, 91);
        intList.add(1, 90);
        intList.add(1, 89);
        assertThat(intList.subList(1, 4).containsAll(Arrays.asList(89, 90, 91)), is(true));
        assertThat(intList.subList(1, 4).size(), is(3));
    }

    @Test
    public void removeAllTest() throws Exception {
        List<Integer> temp = new LinkedList<>();
        temp.add(42);
        temp.add(38);
        intList.addAll(Arrays.asList(22, 38, 43, 42, 55));
        intList.removeAll(temp);
        assertThat(intList.containsAll(Arrays.asList(22, 43, 55)), is(true));
        assertThat(intList.contains(42), is(false));
        assertThat(intList.size(), is(3));
    }

    @Test
    public void retainAllTest() throws Exception {
        List<Integer> temp = new LinkedList<>();
        temp.add(42);
        temp.add(38);
        intList.addAll(Arrays.asList(22, 38, 43, 42, 55));
        intList.retainAll(temp);
        assertThat(intList.containsAll(Arrays.asList(42, 38)), is(true));
        assertThat(intList.contains(22), is(false));
        assertThat(intList.size(), is(4));
    }
}