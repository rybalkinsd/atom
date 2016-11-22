package volatile_example;

public class VolatileExample {
  private static boolean isFinished;

  public static void main(String[] args) throws InterruptedException {
    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 100; i++) System.out.println(isFinished);
    });
    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 100; i++) System.out.println(isFinished);
    });
    Thread changer = new Thread(() -> {
      isFinished = true;
    });

    thread1.start();
    thread2.start();
    Thread.sleep(1);
    changer.start();

    thread1.join();
    thread2.join();
  }
}
