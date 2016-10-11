package ru.atom.streams.practicum;

import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author apomosov
 */
public class Sort {
  /**
   *
   * @param args
   */
  public static void main(@NotNull String[] args) {
    sort(Arrays.stream(args));
  }

  /**
   * @return ordered Stream with sorted Strings
   */
  @NotNull
  public static Stream<String> sort(@NotNull Stream<String> input) {
    throw new NotImplementedException();
  }
}
