package utils;

import java.util.concurrent.atomic.AtomicInteger;

public class SequentialIDGenerator implements IDGenerator {
  private final AtomicInteger current = new AtomicInteger(0);
  @Override
  public int next() {
    return current.getAndIncrement();
  }
}