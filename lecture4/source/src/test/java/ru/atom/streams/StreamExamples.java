package ru.atom.streams;

import org.jetbrains.annotations.NotNull;
import org.junit.*;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author apomosov
 */
public class StreamExamples {
  @NotNull
  private static final List<String> TEST_STRINGS = Arrays.asList("To", "be", "or", "not", "to", "be", "that", "is", "the", "question");
  @NotNull
  private static final int[] TEST_INTS = {4, 8, 15, 16, 23, 42};

  /**
   * This example is taken from:
   * https://habrahabr.ru/company/luxoft/blog/270383/
   * code:
   * https://github.com/Vedenin/java_in_examples/blob/master/stream_api/src/com/github/vedenin/rus/stream_api/BuildTests.java
   */
  @Test
  public void createStreams() throws IOException {
    // Создание стрима из значений
    Stream<String> streamFromValues = Stream.of("a1", "a2", "a3");
    System.out.println("streamFromValues = " + streamFromValues.collect(Collectors.toList())); // напечатает streamFromValues = [a1, a2, a3]

    // Создание стрима из массива
    String[] array = {"a1","a2","a3"};
    Stream<String> streamFromArrays = Arrays.stream(array);
    System.out.println("streamFromArrays = " + streamFromArrays.collect(Collectors.toList())); // напечатает streamFromArrays = [a1, a2, a3]

    Stream<String> streamFromArrays1 = Stream.of(array);
    System.out.println("streamFromArrays1 = " + streamFromArrays1.collect(Collectors.toList())); // напечатает streamFromArrays = [a1, a2, a3]

    // Создание стрима из файла (каждая запись в файле будет отдельной строкой в стриме)
    File file = new File("1.tmp");
    file.deleteOnExit();
    PrintWriter out = new PrintWriter(file);
    out.println("a1");
    out.println("a2");
    out.println("a3");
    out.close();
    Stream<String> streamFromFiles = Files.lines(Paths.get(file.getAbsolutePath()));
    System.out.println("streamFromFiles = " + streamFromFiles.collect(Collectors.toList())); // напечатает streamFromFiles = [a1, a2, a3]

    // Создание стрима из коллекции
    Collection<String> collection = Arrays.asList("a1", "a2", "a3");
    Stream<String> streamFromCollection = collection.stream();
    System.out.println("streamFromCollection = " + streamFromCollection.collect(Collectors.toList())); // напечатает streamFromCollection = [a1, a2, a3]

    // Создание числового стрима из строки
    IntStream streamFromString = "123".chars();
    System.out.print("streamFromString = ");
    streamFromString.forEach((e)->System.out.print(e + " , ")); // напечатает streamFromString = 49 , 50 , 51 ,
    System.out.println();

    // С помощью Stream.builder
    Stream.Builder<String> builder = Stream.builder();
    Stream<String> streamFromBuilder = builder.add("a1").add("a2").add("a3").build();
    System.out.println("streamFromBuilder = " + streamFromBuilder.collect((Collectors.toList()))); // напечатает streamFromFiles = [a1, a2, a3]

    // Создание бесконечных стримов
    // С помощью Stream.iterate
    Stream<Integer> streamFromIterate = Stream.iterate(1, n -> n + 2);
    System.out.println("streamFromIterate = " + streamFromIterate.limit(3).collect(Collectors.toList())); // напечатает streamFromIterate = [1, 3, 5]

    // С помощью Stream.generate
    Stream<String> streamFromGenerate = Stream.generate(() -> "a1");
    System.out.println("streamFromGenerate = " + streamFromGenerate.limit(3).collect(Collectors.toList())); // напечатает streamFromGenerate = [a1, a1, a1]

    // Создать пустой стрим
    Stream<String> streamEmpty = Stream.empty();
    System.out.println("streamEmpty = " + streamEmpty.collect(Collectors.toList())); // напечатает streamEmpty = []

    // Создать параллельный стрим из коллекции
    Stream<String> parallelStream = collection.parallelStream();
    System.out.println("parallelStream = " + parallelStream.collect(Collectors.toList())); // напечатает parallelStream = [a1, a2, a3]
  }

  @org.junit.Test
  public void foreach() {
    TEST_STRINGS.stream().forEach(System.out::println);
    Stream.of("Whether", "'tis", "nobler", "in", "the", "mind", "to", "suffer").forEach(System.out::println);
  }

  @org.junit.Test
  public void sumOfOddCollections() {
    Integer sumOddOld = 0;
    for (Integer i : TEST_INTS) {
      if (i % 2 != 0) {
        sumOddOld += i;
      }
    }
    Assert.assertEquals(new Integer(38), sumOddOld);
  }

  @org.junit.Test
  public void sumOfOddStreams() {
    Assert.assertEquals(38,
        Arrays.stream(TEST_INTS).filter(o -> o % 2 != 0).reduce((s1, s2) -> s1 + s2).orElse(0)
    );
  }

  @org.junit.Test
  public void sumOfOddReduce() {
    Assert.assertEquals(38,
        Arrays.stream(TEST_INTS).parallel().filter(o -> o % 2 != 0).reduce((s1, s2) -> s1 + s2).orElse(0)
    );
  }


}
