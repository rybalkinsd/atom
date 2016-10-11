package ru.atom.streams.practicum;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author apomosov
 */
public class UniqTest {
  @NotNull
  private static final Stream<String> input = Stream.of("Dave", "Eve", "Bob", "Eve", "Carol", "Alice", "Eve", "Carol");

  @Test
  public void testUniq() { //TODO fix this test
    Stream<String> uniq = Uniq.uniq(input);
    Assert.assertEquals(5, uniq.count());//uniq - is terminal operation
    Assert.assertTrue(uniq.anyMatch(element -> element.equals("Alice")));//java.lang.IllegalStateException here, because the stream is closed.
    Assert.assertTrue(uniq.anyMatch(element -> element.equals("Bob")));
    Assert.assertTrue(uniq.anyMatch(element -> element.equals("Carol")));
    Assert.assertTrue(uniq.anyMatch(element -> element.equals("Dave")));
    Assert.assertTrue(uniq.anyMatch(element -> element.equals("Eve")));
  }

  @Test
  public void testNotSameStream(){
    Assert.assertNotSame(input, Uniq.uniq(input));
  }
}
