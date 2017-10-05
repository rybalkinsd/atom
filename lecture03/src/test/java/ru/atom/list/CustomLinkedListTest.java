package ru.atom.list;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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
    }

    @Test
    public void removeTest() throws Exception {
        intList.remove((Integer) 42);
        assertThat(intList.contains(42), is(false));
        assertThat(intList.contains(38), is(true));
        assertThat(intList.size(), is(1));
    }

    @Test
    public void getByIndexTest1() {
        assertThat("Hello".equals(stringList.get(0)), is(true));
        assertThat("Hello".equals(stringList.get(1)), is(false));
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
            String str = stringList.get(-3);
            assertThat("unreachable line", false);
        } catch (ArrayIndexOutOfBoundsException ex) {
            assertThat("reachable line", true);
        }
    }

    @Test
    public void addAllWithIndexTest1() {
        intList.addAll(0, Arrays.asList(11, 111, 1111));
        assertThat(intList.get(0).equals((Integer)11), is(true));
        assertThat(intList.get(1).equals((Integer)111), is(true));
        assertThat(intList.get(2).equals((Integer)1111), is(true));
    }

    @Test
    public void addAllWithIndexTest2() {
        stringList.addAll(stringList.size(), Arrays.asList(" my", " name", " is", " Alexander"));
        stringList.add("!");
        assertThat("!".equals(stringList.get(stringList.size() - 1)), is(true));
    }

    @Test
    public void removeAllTest1() {
        stringList.removeAll(Arrays.asList("Hello", "world!", "lol"));
        assertThat(", ".equals(stringList.get(stringList.size() - 1)), is(true));
    }

    @Test
    public void removeAllTest2() {
        assertThat(stringList.removeAll(Arrays.asList("kek", "lol")), is(false));
    }

    @Test
    public void retainAllTest1() {
        List<Integer> lst = Arrays.asList(11, 111, 1111);
        intList.addAll(0, lst);
        assertThat(intList.retainAll(lst), is(true));
        assertThat(intList.size() == 3, is(true));
        assertThat(intList.get(0).equals((Integer)11), is(true));
        assertThat(intList.get(1).equals((Integer)111), is(true));
        assertThat(intList.get(2).equals((Integer)1111), is(true));
    }

    @Test
    public void addWithIndexTest1() {
        stringList.add(1, "lol");
        assertThat(stringList.size() == 4, is(true));
        assertThat("world!".equals(stringList.get(3)), is(true));
    }

    @Test
    public void removeWithIndexTest1() {
        Integer removedElement = intList.remove(1);
        assertThat(removedElement.equals(38), is(true));
        assertThat(intList.size() == 1, is(true));
    }

}