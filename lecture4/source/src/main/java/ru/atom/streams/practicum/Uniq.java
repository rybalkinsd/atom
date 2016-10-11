package ru.atom.streams.practicum;

import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author apomosov
 */
public class Uniq {
  public static void main(@NotNull String[] args) {
    uniq(Arrays.stream(args));
  }

  /**
   * @return Stream with unique Strings
   */
  @NotNull
  public static Stream<String> uniq(@NotNull Stream<String> input) {
    throw new NotImplementedException();
  }
}
