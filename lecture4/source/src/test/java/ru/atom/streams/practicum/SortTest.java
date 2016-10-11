package ru.atom.streams.practicum;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author apomosov
 */
public class SortTest {
  @NotNull
  private static final Stream<String> input = Stream.of("Dave", "bob", "eve", "Carol", "Alice");

  @Test
  public void testCaseInsensitiveSort() {
    Assert.assertEquals(Stream.of("Alice", "bob", "Carol", "Dave", "eve"), Sort.sort(input));
  }

  @Test
  public void testNotSameCollection(){
    Assert.assertNotSame(input, Sort.sort(input));
  }
}
