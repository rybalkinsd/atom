package ru.atom.lecture10.initialization;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * https://www.ibm.com/developerworks/ru/library/j-jtp0618/
 */
public class UnsafeInitialization {
    public static UnsafeInitialization anInstance;
    public static Set<Object> set = new HashSet<>();
    private Set mySet = new HashSet();

    public UnsafeInitialization() {
        // Небезопасно, потому что anInstance виден всем
        anInstance = this;

        // Небезопасно, потому что SomeOtherClass.anInstance виден всем
        SomeOtherClass.anInstance = this;

        // Небезопасно, потому что SomeOtherClass может сохранить указатель "this"
        // так, что он может быть виден другому потоку
        SomeOtherClass.registerObject(this);

        // Небезопасно, потому что set имеет глобальную видимость
        set.add(this);

        // Небезопасно, потому что мы публикуем указатель на mySet
        mySet.add(this);
        SomeOtherClass.someMethod(mySet);

        // Небезопасно, потому что объект "this" будет виден из нового
        // потока до того, как конструктор завершит работу
        Thread thread = new MyThread(this);
        thread.start();
    }

    private class MyThread extends Thread {

        private Object o;

        public MyThread(Object o) {
            this.o = o;
        }
    }


    public UnsafeInitialization(Collection c) {
        // Небезопасно, потому что "c" может быть видимо из других потоков
        c.add(this);
    }
}