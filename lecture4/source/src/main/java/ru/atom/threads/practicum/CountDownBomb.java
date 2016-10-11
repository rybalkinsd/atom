package ru.atom.threads.practicum;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CountDownBomb {
  private int countDownSeconds;

  public CountDownBomb(int countdownSeconds) {
    this.countDownSeconds = countdownSeconds;
  }

  public void setSleepingBomb() {
    try {
      while (countDownSeconds > 0) {
        System.out.println(countDownSeconds);
        Thread.currentThread().sleep(TimeUnit.SECONDS.toMillis(1));
        countDownSeconds--;
      }
      System.out.println("BOOM!");
    } catch (InterruptedException e) {
      System.out.println("Neutralized!");
    }
  }

  public void setBusyBomb() {
    Random random = new Random();
    while (countDownSeconds > 0) {
      System.out.println(countDownSeconds);
      for (int i = 100000000; i > 0; i--) {
        random.nextInt();
      }
      countDownSeconds--;
    }
    System.out.println("BOOM!");
  }
}