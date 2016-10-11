package ru.atom.collections.practicum;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import ru.atom.collections.practicum.Uniq;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author apomosov
 */
public class UniqTest {
  @NotNull
  private static final List<String> input = Arrays.asList("Dave", "Eve", "Bob", "Eve", "Carol", "Alice", "Eve", "Carol");

  @Test
  public void testUniq() {
    Collection<String> uniq = Uniq.uniq(input);
    Assert.assertEquals(5, uniq.size());
    Assert.assertTrue(uniq.contains("Alice"));
    Assert.assertTrue(uniq.contains("Bob"));
    Assert.assertTrue(uniq.contains("Carol"));
    Assert.assertTrue(uniq.contains("Dave"));
    Assert.assertTrue(uniq.contains("Eve"));
  }

  @Test
  public void testNotSameCollection(){
    Assert.assertNotSame(input, Uniq.uniq(input));
  }
}
