package heap_analysis;

import java.util.ArrayList;

/**
 * Inspect this example with JMC
 * Than take heap dump and analyse it
 *
 * @author Alpi
 * @since 06.12.16
 */
public class GarbageProducer {
  private static final ArrayList<Object> data = new ArrayList<>();

  private void generateGarbage() {
    System.out.println("Generate Garbage ...");
    for (int i = 0; i < 1_000_000_000; i++) {
      Object o = new Object();
      if (i % 10 == 0) {
        data.add(o);
      }
      if (i % 10000 == 0) {
        System.out.println(i + " Objects created, " + i / 10 + " stored");
      }
    }
  }

  private void wait10seconds() throws InterruptedException {
    System.out.println("Wait 10 seconds ...");
    Thread.sleep(10000);
  }

  public static void main(String[] args) throws InterruptedException {
    GarbageProducer garbageProducer = new GarbageProducer();

    garbageProducer.wait10seconds();
    garbageProducer.generateGarbage();
  }
}
