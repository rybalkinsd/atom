package ru.atom.list;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

//@Ignore
public class CustomLinkedListTest {
    private List<Integer> intList = new CustomLinkedList<>();
    private List<String> stringList = new CustomLinkedList<>();
    private List<Integer> intMyList = new CustomLinkedList<>();
    //private List<Integer> intMyListZero = new CustomLinkedList<>();

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
        assertThat(intMyList.size(), is(equalTo(0)));
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

    //Добавленные тесты
    @Test
    public void getTest() throws Exception {
        assertThat(intList.get(1), is(equalTo(38)));
        assertThat(stringList.get(2), is(equalTo("world!")));
    }

    @Test
    public void isEmptyTest() throws Exception {
        assertThat(intMyList.isEmpty(), is(equalTo(true)));
        assertThat(intList.isEmpty(), is(equalTo(false)));
    }

    @Test
    public void lastIndexOfTest() throws Exception {
        assertThat(intList.lastIndexOf(42), is(0));
        assertThat(stringList.lastIndexOf("world!"), is(2));
    }

    @Test
    public void removeTestIndex() throws Exception {
        intList.remove(0);
        assertThat(intList.contains(42), is(false));
        assertThat(intList.contains(38), is(true));
        assertThat(intList.size(), is(1));
    }

    @Test
    public void clearTest() throws Exception {
        intList.removeAll(Arrays.asList(42, 38));
        assertThat(intList.contains(42), is(false));
        assertThat(intList.contains(38), is(false));
        assertThat(intList.size(), is(0));
    }

    @Test
    public void containsAllTest() throws Exception {
        assertThat(intList.containsAll(Arrays.asList(42, 38)), is(true));
        assertThat(intList.containsAll(Arrays.asList(1, 4)), is(false));
    }

    @Test
    public void setTestIndex() throws Exception {
        intList.set(1, 67);
        assertThat(intList.contains(76), is(false));
        assertThat(intList.contains(67), is(true));
        assertThat(intList.size(), is(2));
    }

    @Test
    public void removeTwoTest() throws Exception {
        assertThat(intMyList.remove((Integer) 42), is(false));
    }


}