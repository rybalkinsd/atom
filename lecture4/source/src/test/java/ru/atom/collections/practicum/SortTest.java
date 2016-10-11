package ru.atom.collections.practicum;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import ru.atom.collections.practicum.Sort;

import java.util.Arrays;
import java.util.List;

/**
 * @author apomosov
 */
public class SortTest {
  @NotNull
  private static final List<String> input = Arrays.asList("Dave", "bob", "eve", "Carol", "Alice");

  @Test
  public void testCaseInsensitiveSort() {
    Assert.assertEquals(Arrays.asList("Alice", "bob", "Carol", "Dave", "eve"), Sort.sort(input));
  }

  @Test
  public void testNotSameCollection(){
    Assert.assertNotSame(input, Sort.sort(input));
  }
}
