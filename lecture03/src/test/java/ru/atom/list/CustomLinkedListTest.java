package ru.atom.list;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

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

        stringList.addAll(Arrays.asList(" I", " like", " Java"));
        assertThat(stringList.size(), is(equalTo(3 + 3)));
    }

    @Test
    public void removeTest() throws Exception {
        assertThat(intList.remove((Integer) 42), is(true));

        assertThat(stringList.remove(", "), is(true));
    }

    @Test
    public void getByIndexTest1() {
        assertThat(intList.get(0), is(equalTo(42)));
        assertThat(intList.get(1), is(not(equalTo(42))));

        assertThat(stringList.get(0), is(equalTo("Hello")));
        assertThat(stringList.get(1), is(not(equalTo("Hello"))));
    }

    @Test
    public void getByIndexTest2() {
        try {
            String str = stringList.get(5);
            assertThat("unreachable line", false);
        } catch (ArrayIndexOutOfBoundsException ex) {
            assertThat("reachable line", true);
        }
        try {
            Integer num = intList.get(-3);
            assertThat("unreachable line", false);
        } catch (ArrayIndexOutOfBoundsException ex) {
            assertThat("reachable line", true);
        }
    }

    @Test
    public void addAllWithIndexTest1() {
        intList.addAll(0, Arrays.asList(11));
        assertThat(intList.get(0), is(equalTo(11)));

        stringList.addAll(1, Arrays.asList("I", " like", " Java"));
        assertThat(stringList.get(2), is(equalTo(" like")));
    }

    @Test
    public void removeAllTest1() {
        intList.removeAll(Arrays.asList(38));
        assertThat(intList.size(), is(equalTo(1)));

        stringList.removeAll(Arrays.asList("Hello", "world!", "lol"));
        assertThat(stringList.get(stringList.size() - 1), is(equalTo(", ")));
    }

    @Test
    public void removeAllTest2() {
        assertThat(intList.removeAll(Arrays.asList(1, 2)), is(false));

        assertThat(stringList.removeAll(Arrays.asList("kek", "lol")), is(false));
    }

    @Test
    public void addWithIndexTest1() {
        intList.add(2, 11);
        assertThat(intList.size(), is(equalTo(3)));

        stringList.add(1, "lol");
        assertThat(stringList.size(), is(equalTo(4)));
    }

    @Test
    public void removeWithIndexTest1() {
        Integer removedIntElement = intList.remove(1);
        assertThat(removedIntElement, is(equalTo(38)));

        String removedStringElement = stringList.remove(2);
        assertThat(removedStringElement, is(equalTo("world!")));
    }

    @Test
    public void clearTest() {
        intList.clear();
        assertThat(intList.size(), is(equalTo(0)));

        stringList.clear();
        assertThat(stringList.size(), is(equalTo(0)));
    }

    @Test
    public void indexOfTest() {
        assertThat(intList.indexOf(12), is(equalTo(-1)));

        assertThat(stringList.indexOf(", "), is(equalTo(1)));
    }

}