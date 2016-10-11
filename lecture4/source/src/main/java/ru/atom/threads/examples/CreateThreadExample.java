package ru.atom.threads.examples;

import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public class CreateThreadExample {
  public static class HelloRunnable implements Runnable {
    public void run() {
      System.out.println("Hello from a thread, created via Runnable implementation!");
    }
  }

  public void createThreadWithRunnableInstance() {
    (new Thread(new HelloRunnable())).start();
  }

  public void createThreadWithInnerClass() {
    (new Thread(new Runnable(){
      @Override
      public void run() {
        System.out.println("Hello from a thread created via inner class!");
      }
    })).start();
  }

  public void createThreadWithLambda() {
    (new Thread(() -> System.out.println("Hello from a thread created via lambda!"))).start();
  }
}
