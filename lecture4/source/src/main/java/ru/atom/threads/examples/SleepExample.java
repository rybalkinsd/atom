package ru.atom.threads.examples;

import org.jetbrains.annotations.NotNull;

public class SleepExample {
  public static void main(@NotNull String args[])
      throws InterruptedException {
    singKaraoke(new String[]{
        "3", "2", "1",
        "I've lived a life that's full",
        "I've traveled each and every highway",
        "And more, much more than this",
        "I did it my way"
    });
  }

  private static void singKaraoke(@NotNull String[] song) throws InterruptedException {
    for (int i = 0; i < song.length; i++) {
      System.out.println(song[i]);
      Thread.sleep(2000);
    }
  }
}