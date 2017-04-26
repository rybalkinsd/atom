package ru.atom.lecture10.threadlocal;

/**
 * @author Alpi
 * @since 21.11.16
 */
public class ThreadLocalExample {
    public static class MyRunnable implements Runnable {
        //This container contains different references for different threads
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

        @Override
        public void run() {
            threadLocal.set((int) (Math.random() * 100));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
