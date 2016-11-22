package ordering;

public class ReorderingExample {
  boolean first = false;
  boolean second = false;

  void setValues() {
    first = true;
    second = true;
  }

  void checkValues() {
    while (!second) ;
    assert first;
  }

  /**
   * It can possibly fail
   */
  public static void main(String[] args) throws InterruptedException {
    ReorderingExample r = new ReorderingExample();
    Thread thread1 = new Thread(() -> r.setValues());
    Thread thread2 = new Thread(() -> r.checkValues());

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();
  }
}