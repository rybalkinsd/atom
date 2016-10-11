package ru.atom.collections.practicum;

import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author apomosov
 */
public class Uniq {
  public static void main(@NotNull String[] args) {
    uniq(Arrays.asList(args));
  }
  @NotNull
  public static Collection<String> uniq(@NotNull Collection<String> input) {
    throw new NotImplementedException();
  }
}
