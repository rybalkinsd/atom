package ru.atom.list;

import org.junit.Before;
import org.junit.Test;

import ru.atom.list.CustomLinkedList;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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
        intList.addAll(Arrays.asList());
        assertThat(intList.size(), is(equalTo(2 + 5)));

        stringList.addAll(Arrays.asList("I", "like", "Java"));
        stringList.addAll(Arrays.asList());
        assertThat(stringList.size(), is(equalTo(3 + 3)));
    }

    @Test
    public void removeTest() throws Exception {
        assertThat(intList.remove((Integer) 42), is(true));
        assertThat(intList.remove((Integer) 42), is(false));

        assertThat(stringList.remove(", "), is(true));
        assertThat(stringList.remove(", "), is(false));
    }

    @Test
    public void getByIndexTest1() {
        assertThat(intList.get(0), is(equalTo((Integer)42)));
        assertThat(intList.get(1), is(not(equalTo((Integer)42))));

        assertThat(stringList.get(0), is(equalTo("Hello")));
        assertThat(stringList.get(1), is(not(equalTo("Hello"))));
    }

    @Test
    public void getByIndexTest2() {
        try {
            intList.get(5);
            assertThat("unreachable line", false);
        } catch (IndexOutOfBoundsException ex) {
            assertThat("reachable line", true);
        }
        try {
            intList.get(-3);
            assertThat("unreachable line", false);
        } catch (IndexOutOfBoundsException ex) {
            assertThat("reachable line", true);
        }

        try {
            stringList.get(5);
            assertThat("unreachable line", false);
        } catch (IndexOutOfBoundsException ex) {
            assertThat("reachable line", true);
        }
        try {
            stringList.get(-3);
            assertThat("unreachable line", false);
        } catch (IndexOutOfBoundsException ex) {
            assertThat("reachable line", true);
        }
    }

    @Test
    public void indexOfTest() {
        assertThat(intList.indexOf((Integer)38), is(1));
        assertThat(intList.indexOf((Integer)55), is(-1));
        assertThat(intList.indexOf(null), is(-1));

        assertThat(stringList.indexOf("world!"), is(2));
        assertThat(stringList.indexOf("Java"), is(-1));
        assertThat(stringList.indexOf(null), is(-1));
    }

    @Test
    public void clearTest() {
        intList.clear();
        assertThat(intList.isEmpty(), is(true));

        stringList.clear();
        assertThat(stringList.isEmpty(), is(true));
    }

    @Test
    public void addRemoveNullTest() {
        assertThat(intList.add(null), is(true));
        assertThat(intList.contains(null), is(true));
        assertThat(intList.remove(null), is(true));

        assertThat(stringList.add(null), is(true));
        assertThat(stringList.contains(null), is(true));
        assertThat(stringList.remove(null), is(true));
    }

    @Test
    public void containsAllTest() {
        assertThat(intList.containsAll(Arrays.asList(38, 42)), is(true));
        assertThat(intList.containsAll(Arrays.asList(38, 42, 13)), is(false));

        assertThat(stringList.containsAll(Arrays.asList("Hello", "world!")), is(true));
        assertThat(stringList.containsAll(Arrays.asList("Hello", "Goodby")), is(false));
    }

    @Test
    public void lastIndexOfTest() {
        assertThat(intList.lastIndexOf((Integer)38), is(1));
        assertThat(intList.lastIndexOf((Integer)55), is(-1));
        assertThat(intList.lastIndexOf(null), is(-1));

        assertThat(stringList.lastIndexOf("Hello"), is(0));
        assertThat(stringList.lastIndexOf("Java"), is(-1));
        assertThat(stringList.lastIndexOf(null), is(-1));
    }

    @Test
    public void removeByIndex1() {
        assertThat(intList.remove(0), is(equalTo((Integer)42)));

        assertThat(stringList.remove(2), is(equalTo("world!")));
    }

    @Test
    public void removeByIndexTest2() {
        try {
            intList.remove(5);
            assertThat("unreachable line", false);
        } catch (IndexOutOfBoundsException ex) {
            assertThat("reachable line", true);
        }
        try {
            intList.remove(-3);
            assertThat("unreachable line", false);
        } catch (IndexOutOfBoundsException ex) {
            assertThat("reachable line", true);
        }

        try {
            stringList.remove(5);
            assertThat("unreachable line", false);
        } catch (IndexOutOfBoundsException ex) {
            assertThat("reachable line", true);
        }
        try {
            stringList.remove(-3);
            assertThat("unreachable line", false);
        } catch (IndexOutOfBoundsException ex) {
            assertThat("reachable line", true);
        }
    }

}
