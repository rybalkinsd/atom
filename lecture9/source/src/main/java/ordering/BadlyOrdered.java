package ordering;

public class BadlyOrdered {
  boolean a = false;
  boolean b = false;

  void threadOne() {
    a = true;
    b = true;
  }

  boolean threadTwo() {
    boolean r1 = b; // sees true
    boolean r2 = a; // sees false
    boolean r3 = a; // sees true
    return (r1 && !r2) && r3; // returns true
  }

  public static void main(String[] args) throws InterruptedException {//Rarely reproducible, but possible
    for (int i = 0; i < 100000; i++) {
      BadlyOrdered badlyOrdered = new BadlyOrdered();
      Thread thread1 = new Thread(badlyOrdered::threadOne);
      Thread thread2 = new Thread(() -> System.out.println(badlyOrdered.threadTwo()));

      thread1.start();
      thread2.start();

      thread1.join();
      thread2.join();
    }
  }
}