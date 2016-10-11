package ru.atom.threads.practicum;

import org.junit.Test;

/**
 * @author apomosov
 */
public class NeutralizeBombTest {
  @Test
  public void neutralizeSleepingBomb() throws InterruptedException {
    CountDownBomb countDownBomb = new CountDownBomb(10);
    Thread bombThread = new Thread(countDownBomb::setSleepingBomb);
    bombThread.start();//bomb starts to countdown in parallel thread
    //TODO Neutralize it, NOW!
    bombThread.join();//here we block until bomb thread finishes it's execution
    System.out.println("Bomb thread is dead now");
  }

  @Test
  public void neutralizeBusyBomb() throws InterruptedException {
    CountDownBomb countDownBomb = new CountDownBomb(10);
    Thread bombThread = new Thread(countDownBomb::setBusyBomb);
    bombThread.start();//bomb starts to countdown in parallel thread
    //TODO Neutralize it, NOW!
    bombThread.join();//here we block until bomb thread finishes it's execution
    System.out.println("Bomb thread is dead now");
  }
}
