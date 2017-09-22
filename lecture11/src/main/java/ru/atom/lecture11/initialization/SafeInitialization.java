package ru.atom.lecture11.initialization;

import java.util.HashSet;
import java.util.Set;

/**
 * https://www.ibm.com/developerworks/ru/library/j-jtp0618/
 */
public class SafeInitialization {
    private Object me;
    private Set<Object> set = new HashSet<>();
    private Thread thread;

    public SafeInitialization() {
        // Безопасно, потому что "me" невидимо из любого другого потока
        me = this;

        // Безопасно, потому что "set" невидимо из любого другого потока
        set.add(this);

        // Безопасно, потому что MyThread не запустится, пока не будет завершена работа конструктора
        // и конструктор не опубликует указатель
        thread = new MyThread(this);
    }

    public void start() {
        thread.start();
    }

    private class MyThread extends Thread {

        private Object o;

        public MyThread(Object o) {
            this.o = o;
        }
    }
}